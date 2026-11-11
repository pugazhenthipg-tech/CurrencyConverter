# Currency Converter

A Java console application for converting amounts between USD, INR, EUR, GBP, and JPY using fixed sample exchange rates.

## Features

- Supports `USD` (US Dollar), `INR` (Indian Rupee), `EUR` (Euro), `GBP` (British Pound), and `JPY` (Japanese Yen)
- Uses fixed sample rates relative to 1 USD (as of `2026-10-26`, not live market rates)
- Input validation and repeat conversion prompt
- Non-interactive `--demo` mode

## Compile and Run

```bash
javac CurrencyConverter.java
java CurrencyConverter
```

Run demo mode:

```bash
java CurrencyConverter --demo
```
