# Performance Results

Do not add estimated, remembered, or single-stopwatch values. Record only results produced by a documented, repeatable experiment.

## Current status

No trusted performance baseline has been recorded yet.

The deterministic workload is complete. The startup benchmark protocol must be finalized before its numbers are treated as evidence.

## Environment

```text
Device: Not recorded
Device type: Emulator / physical
Android version: Not recorded
Refresh rate: Not recorded
Application commit: Not recorded
Build type: benchmark
R8: disabled
Startup mode: Not recorded
Compilation mode: Not recorded
Iterations: Not recorded
Thermal/device conditions: Not recorded
```

## Baselines

### Startup

```text
Endpoint: TTID / TTFD — not selected
Metric: StartupTimingMetric
p50: Not measured
p90: Not measured
p95: Not measured
Minimum: Not measured
Maximum: Not measured
Trace location: Not recorded
```

### Feed scrolling

```text
Workload: Not finalized
Metric: FrameTimingMetric
Frame-duration percentiles: Not measured
Frame-overrun percentiles: Not measured
Jank result: Not measured
Trace location: Not recorded
```

### Build performance

```text
Gradle Profiler scenarios: Not created
Clean build: Not measured
No-op build: Not measured
Non-ABI change: Not measured
ABI change: Not measured
Resource change: Not measured
```

### Memory, network, and battery

Not measured.

## Experiment template

Copy this section for each experiment.

### Experiment: `<name>`

#### Question

State one precise question.

#### Hypothesis

Describe the expected cause and predicted direction of change before editing code.

#### Controlled conditions

```text
Device:
Android version:
Before commit:
After commit:
Build type:
R8 state:
Startup mode:
Compilation mode:
Metric:
Iterations:
Application state:
Other controls:
```

#### Workload

Define setup, measured action, and completion condition.

#### Before

```text
p50:
p90:
p95:
Minimum:
Maximum:
Trace:
```

#### Change

Describe one focused implementation change.

#### After

```text
p50:
p90:
p95:
Minimum:
Maximum:
Trace:
```

#### Conclusion

- Did the evidence support the hypothesis?
- What was the absolute change?
- What was the percentage change?
- Did variance improve or worsen?
- Were any other metrics harmed?
- What remains uncertain?
