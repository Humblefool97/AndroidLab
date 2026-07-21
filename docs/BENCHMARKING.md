# Benchmarking Guide

## Definition

A benchmark is a controlled, repeatable performance experiment.

It requires:

- A precise performance question.
- A deterministic workload.
- A metric that answers the question.
- Documented experimental conditions.
- Multiple measured iterations.
- A baseline for comparison.
- Incremental validation after a focused change.

The basic loop is:

```text
Benchmark → diagnose → change one variable → benchmark again
```

Benchmarking determines whether performance changed. Profiling and tracing help explain why.

## Benchmarking, profiling, and monitoring

- **Benchmark:** measures a controlled workload and detects improvement or regression.
- **Profiler/Perfetto:** investigates where time or resources were consumed.
- **Production monitoring:** observes real users across devices, networks, and usage patterns.

These tools complement one another and should not be treated as substitutes.

## Macrobenchmark and Microbenchmark

### Macrobenchmark

Measures complete user journeys such as:

- Application startup.
- Feed scrolling.
- Navigation.
- Animation.

The benchmark APK drives a separate target application APK.

### Microbenchmark

Measures focused operations such as:

- Parsing.
- Sorting.
- Formatting.
- Data transformation.
- A specific algorithm.

A microbenchmark result does not automatically predict end-to-end user experience.

## Define startup precisely

“Startup time” is ambiguous.

### TTID: Time to Initial Display

Time until the application produces its initial frame.

### TTFD: Time to Full Display

Time until the application’s important content is ready and it reports itself fully drawn.

Example:

```text
Skeleton frame appears: 300 ms → TTID
Interactive feed appears: 900 ms → TTFD
```

A fast TTID can coexist with a poor TTFD.

The initial AndroidLab startup question is:

> On a fixed device and benchmark build, how long does a cold process start take until the feed reaches its explicitly defined display state?

The exact end state must be selected before interpreting results.

## Startup modes

- **Cold:** application process is not running.
- **Warm:** process remains but the activity must be recreated.
- **Hot:** process and activity remain and are brought to the foreground.

Cold startup does not imply:

- Fresh installation.
- Cleared application data.
- Device reboot.
- Empty operating-system caches.

## Compilation modes

Android runtime performance depends on compilation state.

- **None:** little or no ahead-of-time compilation; more work may be handled through JIT.
- **Partial:** important code is compiled, often using a Baseline Profile.
- **Full:** application code is extensively compiled ahead of time.

Never compare before and after measurements that silently use different compilation modes. The mode must be explicitly selected and recorded.

## Setup and measured work

Each experiment should separate preparation from the operation answering the question.

Startup:

```text
Setup: press Home and establish app state
Measure: launch app and reach the selected startup endpoint
```

Scrolling:

```text
Setup: launch app, wait for feed, reset list position
Measure: perform fixed swipes and allow frames to settle
```

Detail navigation:

```text
Setup: launch app and locate a fixed item
Measure: select the item and wait for detail content
```

Every measured action needs an explicit completion condition.

## Metrics

Choose the metric from the question.

- `StartupTimingMetric`: startup TTID and, when correctly reported, TTFD.
- `FrameTimingMetric`: frame duration and deadline overrun during UI interactions.
- `TraceSectionMetric`: duration or count of a specifically instrumented critical section.
- Memory and allocation tooling: memory footprint, allocations, garbage collection, and leaks.
- Power tooling: power-related behaviour on supported physical hardware.

A metric is not a completion condition. For example, startup timing collects platform timing while waiting for `feed_list` confirms that the intended screen was reached.

## Frame deadlines

Approximate frame intervals:

```text
60 Hz  → 16.67 ms
90 Hz  → 11.11 ms
120 Hz →  8.33 ms
```

Frame-deadline overrun is more robust than applying a fixed 16.67 ms threshold to every device. Missing a deadline can create visible jank.

## Builds

Do not use debug-build results for production performance claims.

A benchmark target should normally be:

- Non-debuggable.
- Profileable.
- Release-like.
- Built with a documented optimization configuration.

AndroidLab currently has a non-debuggable benchmark build inherited from release. R8 optimization is disabled in both release and benchmark, so initial measurements represent an unoptimized laboratory artifact—not a production-optimized application.

This can later become a controlled R8-disabled versus R8-enabled experiment.

## Repetition and distributions

Individual runs vary because of:

- CPU scheduling.
- Garbage collection.
- Thermal state.
- Background operating-system work.
- Caching.
- Runtime compilation.

Stable measurements form a reasonably consistent distribution; they are not identical.

Report:

- p50 or median for typical behaviour.
- p90/p95/p99 for increasingly slow tail behaviour.
- Minimum and maximum where useful.
- Iteration count and experimental metadata.

Five runs are sufficient to learn the workflow but insufficient for trustworthy p90/p95 estimates. Use more iterations for local tail analysis and production telemetry for population-level percentiles.

Do not remove an outlier merely because it is inconvenient. Inspect its trace and determine whether it represents environmental noise, a real application path, or an invalid iteration.

## Required metadata

Every saved result should include:

```text
Device:
Android version:
Application commit:
Build type:
R8 state:
Startup mode:
Compilation mode:
Metric:
Iteration count:
Application data state:
Relevant device conditions:
```

A number without these conditions is incomplete.

## Existing AndroidLab benchmark

The project currently contains a cold-start Macrobenchmark with:

- `StartupTimingMetric`
- `StartupMode.COLD`
- Five iterations
- A non-debuggable target benchmark build

Known limitations before recording a trusted baseline:

1. Setup and measured work are not explicitly separated.
2. Compilation mode is not explicitly documented in the test.
3. The test launches the activity but does not explicitly wait for `feed_list`.
4. Feed usability and fully-drawn reporting are not yet defined.
5. Five iterations do not support meaningful tail percentiles.
6. R8 is disabled.
7. Emulator measurements are suitable for learning, not production conclusions.

## First measurement protocol

When code changes are authorized:

1. Select TTID or TTFD as the precise endpoint.
2. Move non-measured preparation into setup.
3. Declare a compilation mode.
4. Wait for the deterministic `feed_list` state.
5. Run the benchmark on the emulator to understand output and traces.
6. Record every iteration and investigate variation.
7. Repeat on a physical device under documented conditions.
8. Save the baseline in `RESULTS.md`.
9. Introduce only one controlled bottleneck.
