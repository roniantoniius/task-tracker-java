export enum TugasPriority {
  TINGGI = "TINGGI",
  SEDANG = "SEDANG",
  RENDAH = "RENDAH",
}

// Type guard for TaskPriority
// eslint-disable-next-line @typescript-eslint/no-explicit-any
export function isTugasPriority(value: any): value is TugasPriority {
  return Object.values(TugasPriority).includes(value);
}