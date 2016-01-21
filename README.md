# Auction

The problem statement misses a number of important details. Let's try to
make resaonable assumptions.

## Thread-safety

A single-threaded application won't need the KV store, it could keep everything
in its objects state. A single-threaded application is unlikely in a practical
case, when several bid requests are probably coming in parlallel.

We need to serialize write access to the current bid information.

We can imagine that our auction-processing engine, being trivial, is so fast that we
can just queue up requests coming from I/O bound concurrent threads (e.g. network
requests) and process them all sequentially. An event loop-based single-process
server (node.js? nginx with built-in lua?) could work well in this setup.
But for the sake of providing an example, let's assume we don't have such a
luxury provided by runtime.

It's reasonable to assume the KV store providing atomic updates,
but nothing more. Specifically, it does not offer a compare-and-swap
operation.

Implementing a sequential access algorithm without such an operation
is pretty involved, and usually requires data structures depending on
the number of threads. An atomic compare-and-swap facility or a proper locking
mechanism is best left to the OS or a runtime to implement.
(A transactional database would be even better.)

Since our KV store is in-memory, we realistically cannot expect to win any
performance by doing two parallel writes at a time. (With a disk-based DB,
we could be writing to different HDDs or different flash blocks in an SSD
in parallel.) Thus we only need one global lock, as opposed to one lock
per transaction in flight.

## Auction identity

The problem statement says that item names are unique. This lets us
use the itemname directly as an auction identifier.

If not for that, we'd have to generate an unique ID for each registered
auction and store the mapping in the KV store.


## Providing current bid information

The problem statement offers to only give the bidders _"the status of the auction
if there is any, if the item is sold, it should return the information
regarding the price sold and to whom it was sold to"_.

This is clearly insufficient for bidding, so I provide the last bid amount
for active auctions, too.

## Implementation

I've implemented this in Java to demonstrate separation of concerns, publishing
only interfaces, and static typing also helps greatly. A number of corners were cut,
all hopefully commented in the code. Tests for each of the important interfaces
are provided.

### Nullness annotations

For some reason both my JDK installations lacked `javax.annotation.Nonnull` and friends.
Instead of installing anything, I marked nullness in comments. So, this part has not been
seriously statically inspected, e.g. by Idea.

### Tests

Tests use a standard `JUnit4`. All reasonably interesting cases of public interfaces uses
are covered.

## Parts remaining after assembly

I found no use for a unique ID generator.

If auctioned items' names were not unique, I could generate unique IDs for them
and return them from `enlistItem`, then use with all other methods where
an auction identity is required.

It would be a pretty trivial change in the code.

