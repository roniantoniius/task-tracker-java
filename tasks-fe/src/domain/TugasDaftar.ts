import Tugas from "./Tugas";

interface TaskList {
  id: string | undefined;
  nama: string;
  deskripsi: string | undefined;
  hitung: number | undefined;
  progress: number | undefined;
  tugas: Tugas[] | undefined;
}
export default TaskList;
