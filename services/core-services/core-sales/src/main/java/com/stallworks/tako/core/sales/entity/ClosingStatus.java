package com.stallworks.tako.core.sales.entity;

// Mirrors the BALANCED/OVER/SHORT status the Reconciliation page already
// computes client-side (expected cash vs. actual cash counted) — recorded
// here so it survives past the moment the shift is closed.
public enum ClosingStatus {
    BALANCED,
    OVER,
    SHORT
}
