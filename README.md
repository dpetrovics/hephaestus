# The Hephaestus Proposal

The Phoenix Rises Again

## Installation

Download from https://github.com/dpetrovics/hephaestus. There's no installation
now... just raw, unmediated knowledge, available for brain-mainlining.

## Usage

We're developing in Emacs, mostly, using the repl. So old school.

## Testing

Run the tests with Leiningen:

```bash
lein test
```

# Rough Plan

The plan is to follow Susskind's [Quantum Mechanics: The Theoretical
Minimum](https://www.amazon.com/Quantum-Mechanics-Theoretical-Leonard-Susskind/dp/0465062903)
and implement as much as we can. Here's a sketch:

## Chapter 1: Systems and Experiments

The first chapter tries to beat us over the head with the fact that QUANTUM IS
WEIRD! We're going to have to use a random number generator to implement the
"measure" function. This is not something that's allowed in classical mechanics.

Susskind describes the Apparatus; we can use the descriptions in this section to
write some tests that we'll try to make pass as we move forward.

Also, a primer on complex numbers and the linear algebra we'll need, once we
surrender and do it "right", instead of trying to force our real coordinates
through.

## Chapter 2: Quantum States

This chapter covers:

- Quantum state, and its representation as a 2d complex vector. (We're going to
  try and figure out an "easier" implementation with real numbers to start,
  since Susskind hasn't motivated yet why we need complex numbers. Well, he sort
  of has, since the spherical coordinates are going to be messy.)

- The idea of a "basis", and the idea that a state can be a superposition of two
  vectors. We won't have the ability to talk about that if we don't surrender
  and go to complex numbers. But we can figure it out.

## Chapter 3: Principles of Quantum Mechanics

This chapter introduces measurements, and the idea of a "measurement operator".
This is where interface and implementation get confusing. REALLY, we should keep
the eigenvectors separate from the actual measurement results.

You can call the result whatever you want; doesn't have to be 1 and -1, for
example.

But then it would be nice, if we do go to complex numbers, to show that we can
get the Pauli matrices back out of our functions.

The big thing we need here is a "measure" function that can take a state and
some measurement direction, and return:

- a measurement value
- a new state, "collapsed"!

I think this is going to force us to think hard about how we're representing our
states internally. What basis are we using? And what basis do you use when you
give someone the direction in which you'd like to measure?

We can't use a "basis" at all if we stay in real coordinates, since we have this
pesky result that in 3-space, opposite directions are orthogonal in state-space.

## Chapter 4:

This chapter covers, finally, time evolution! The quantum state evolves
deterministically. The Schrodinger equation tells us how.

### Schrodinger's Equation

So we get to implement a "tick" function that rolls the state forward in time.

We also talk about expectation values. We can write a test showing that if we
prepare and then measure, over and over, the average value we get out for spin
matches up with classical results.

### Phase Factors

"Phase factors" come in. We have to move, by this point, to complex numbers, so
we can add a function to multiply the state by a phase factor, and show that we
still pass tests.

### Commutators

By now we have to by fully complex. Operators that commute with the Hamiltonian
don't have their measurements messed with over time. That's because their basis
vectors line up. We can implement a few operators and show what happens.

This is our setup for the uncertainty principle.

## License

Copyright © 2020 Dave and Sam.

Distributed under the Eclipse Public License 2.0.
