export enum TugasStatus {
  BUKA = "BUKA",
  TUTUP = "TUTUP",
}
// eslint-disable-next-line @typescript-eslint/no-explicit-any
export function isTugasStatus(value: any): value is TugasStatus {
  return Object.values(TugasStatus).includes(value);
}
