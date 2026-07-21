# Android Performance Lab

## Goal

Build Staff-level Android performance skills through controlled, empirical experiments rather than speculative optimization.

Every experiment follows the same loop:

1. Define one precise performance question.
2. Create a deterministic workload.
3. Record the device, build, compilation mode, app state, and metric.
4. Measure a baseline over repeated iterations.
5. Use profiling or tracing to form a root-cause hypothesis.
6. Change one meaningful variable.
7. Repeat the identical benchmark.
8. Record the result and check for regressions.

The learning cadence uses the 3C framework:

- **Concept:** understand the underlying performance principle.
- **Context:** connect it to Android and this project.
- **Code:** perform one small, measurable experiment.

## Current project

```text
:app
 ├── :core:data
 ├── :core:ui
 ├── :feature:feed
 └── :feature:detail

:feature:feed
 ├── :core:common
 ├── :core:data
 └── :core:ui

:feature:detail
 ├── :core:common
 ├── :core:data
 └── :core:ui

:core:data
 └── :core:common

:core:ui
 └── :core:common
```

The deterministic journey is:

```text
Cold launch → feed → fixed scroll → item 1 → detail → back
```

The feed contains exactly 1,000 fixed local items. It has no network dependency, random identifiers, current-time data, permission dialogs, or first-run flow.

## Phase 1: Measurement foundation

Status: in progress.

1. Learn benchmarking terminology and experimental design.
2. Define cold-start TTID and TTFD precisely.
3. Make startup setup and measured work explicit.
4. Wait for the benchmark-visible `feed_list` state.
5. Choose and record an explicit compilation mode.
6. Run startup measurements on the emulator to learn the workflow.
7. Run credible baselines on a physical device.
8. Add a feed-scrolling benchmark using frame metrics.
9. Record baselines in `RESULTS.md`.

No deliberate runtime bottleneck should be introduced until these measurements are trustworthy.

## Phase 2: Build performance

Use Gradle Profiler and Build Scans where appropriate.

Experiments:

1. Clean, no-op, and incremental build scenarios.
2. ABI-changing versus non-ABI-changing Kotlin edits.
3. Configuration cache enabled versus disabled.
4. Build cache enabled versus disabled.
5. `api` versus `implementation` and recompilation blast radius.
6. KAPT versus KSP.
7. Eager versus lazy task registration.
8. Non-incremental versus incremental custom tasks.
9. Copy-pasted module configuration versus convention plugins.
10. Local versus remote build cache.
11. Dependency and module graph analysis.

If the current project is too small to expose reliable differences, generate synthetic modules specifically for build experiments. Do not bloat the runtime workload merely to make builds slower.

## Phase 3: UI responsiveness

Experiments:

1. Heavy initialization during startup.
2. Main-thread CPU, disk, or database work.
3. Time to initial display versus time to full display.
4. Missing Baseline Profile versus generated Baseline Profile.
5. Compose parameter instability and recomposition.
6. Missing stable list keys.
7. Expensive item work during scrolling.
8. View hierarchy depth and overdraw.
9. R8-disabled versus R8-enabled benchmark artifacts.

Use Macrobenchmark metrics to prove changes and Perfetto traces to diagnose causes.

## Phase 4: Memory

Experiments:

1. Activity or View lifecycle leak.
2. Long-lived callback retaining UI state.
3. Oversized image allocations.
4. Ineffective image caching.
5. Excessive allocation and garbage collection during scrolling.
6. Behaviour under background and memory pressure.

Use repeatable navigation loops, heap analysis, allocation evidence, and LeakCanary where appropriate.

## Phase 5: Network

Experiments:

1. Request caching.
2. Request coalescing and batching.
3. N+1 requests.
4. Compression and payload size.
5. Polling versus event-driven or scheduled synchronization.
6. Prefetching latency versus data and battery trade-offs.
7. Slow, unreliable, and offline network conditions.

## Phase 6: Battery and background work

Experiments:

1. Unconstrained versus constrained WorkManager jobs.
2. Batched versus frequent background work.
3. Polling frequency.
4. Wake-lock lifetime.
5. Location accuracy and update frequency.
6. Doze and application standby behaviour.
7. Prefetching and synchronization resource budgets.

Every background-component design must explain its effects on UI, network, memory, and battery—not just its functionality.

## Phase 7: Production-oriented performance

Local benchmarks cannot represent the complete device and user population.

Design instrumentation for:

- Device and Android-version segments.
- App-version and build segments.
- Network types and quality.
- TTID and TTFD percentiles.
- Slow and frozen frames.
- Memory pressure and crashes.
- Background-work success, latency, and resource use.

Use p50 for typical behaviour and p90/p95/p99 for tail behaviour. Define product SLOs from production evidence rather than treating Android Vitals failure thresholds as ideal targets.

## Rules

- Do not benchmark debug builds for production claims.
- Do not compare different devices or compilation modes as though only code changed.
- Do not optimize from intuition alone.
- Do not combine unrelated fixes into one experiment.
- Do not discard an outlier without investigating or documenting it.
- Prefer a small measured change over a broad speculative refactor.
- Use emulators to learn tools; use representative physical devices for credible runtime conclusions.
- Record measurements before and after every optimization.

## Immediate next task

Continue the benchmarking lessons without changing code until explicitly requested. The next implementation task, when authorized, is to make the cold-start benchmark explicitly define setup, completion, and compilation conditions before collecting the first baseline.
