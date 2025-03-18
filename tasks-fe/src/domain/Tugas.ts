import { TugasPriority } from "./TugasPriority";
import { TugasStatus } from "./TugasStatus";
interface Tugas {
  id: string | undefined;
  nama: string;
  deskripsi: string;
  waktuDeadline: Date | undefined;
  status: TugasStatus| undefined;
  prioritas: TugasPriority;
}
export default Tugas;
