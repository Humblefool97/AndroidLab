# Learning Log

## 2026-07-21

### Project foundation

Completed:

- Created the multi-module Android project.
- Standardized modules on compile SDK 37.
- Configured the Macrobenchmark module to target `:app`.
- Initialized Git on `main`.
- Verified the debug build.

Reference commit:

```text
3d15c98 Initialize Android performance lab scaffold
```

### Deterministic workload

Completed:

- Added `FeedItem` domain model.
- Added a deterministic repository containing exactly 1,000 local items.
- Added repository determinism tests.
- Moved the Compose theme into `:core:ui`.
- Added a Compose feed with stable IDs and test-visible tags.
- Added a Views-based detail activity.
- Added feed-to-detail and back navigation.
- Verified launch, scrolling, detail navigation, and back navigation on the emulator.
- Verified debug build, unit tests, and benchmark APK build.

Reference commit:

```text
5bbec6d Add deterministic performance workload
```

### Benchmarking lessons

Covered:

1. A benchmark is a controlled, repeatable performance experiment.
2. Benchmarking measures whether performance changed; profiling explains why.
3. TTID and TTFD answer different startup questions.
4. A stable workload is deterministic, not necessarily fast.
5. Repeated results naturally vary and should be summarized as distributions.
6. p50 represents typical behaviour; p90/p95 represent progressively slower tails.
7. Device, build, startup mode, compilation mode, and app state must be controlled.
8. Cold startup does not mean a fresh install or empty operating-system cache.
9. Setup work must be separated from the action being measured.
10. Every measured action requires an explicit completion condition.
11. Metrics must be chosen from the performance question.
12. Debug builds are unsuitable for production performance claims.
13. AndroidLab currently benchmarks an R8-disabled artifact.
14. Macrobenchmark produces both measurements and Perfetto traces.
15. Metrics identify where to investigate; traces help determine the cause.

### Current state

- No trusted baseline has been recorded.
- No deliberate performance bottleneck has been introduced.
- The existing startup benchmark is educational but its protocol needs refinement.
- Benchmarking lessons are continuing without code changes until explicitly authorized.

### Next lesson

Learn how to judge benchmark reliability:

- Warm-up and runtime compilation effects.
- Environmental noise.
- Thermal throttling.
- Outliers versus real tail behaviour.
- Determining whether a before/after difference exceeds measurement noise.

### Next implementation task

Only when explicitly authorized:

1. Define whether the first startup result targets TTID or TTFD.
2. Separate setup from measured startup work.
3. Select an explicit compilation mode.
4. Wait for `feed_list`.
5. Run the first educational benchmark.
6. Inspect its numerical output and corresponding Perfetto traces.
