import React, { useEffect, useState } from "react";
import { Button, Input, Textarea, Spacer, Card } from "@nextui-org/react";
import { ArrowLeft } from "lucide-react";
import { useAppContext } from "../AppProvider";
import { useNavigate, useParams } from "react-router-dom";
import axios from "axios";

const CreateUpdateTaskListScreen: React.FC = () => {
  const { state, api } = useAppContext();
  const { listId } = useParams<{ listId: string }>();
  const navigate = useNavigate();

  const [isUpdate, setIsUpdate] = useState(false);
  const [error, setError] = useState("");
  const [nama, setNama] = useState("");
  const [deskripsi, setDeskripsi] = useState<string | undefined>("");

  // Cari task list berdasarkan id
  const findTaskList = (taskListId: string) => {
    const filteredTaskLists = state.taskLists.filter(
      (tl) => tl.id === taskListId
    );
    if (filteredTaskLists.length === 1) {
      return filteredTaskLists[0];
    }
    return null;
  };

  const populateTaskList = (taskListId: string) => {
    const taskList = findTaskList(taskListId);
    if (taskList) {
      console.log("FOUND TASK LIST");
      setNama(taskList.nama);
      setDeskripsi(taskList.deskripsi);
      setIsUpdate(true);
    }
  };

  useEffect(() => {
    if (listId) {
      console.log(`ID is ${listId}`);
      if (state.taskLists.length === 0) {
        console.log("Fetching task lists");
        api.fetchTaskLists().then(() => populateTaskList(listId));
      } else {
        populateTaskList(listId);
      }
    }
  }, [listId]);

  const createUpdateTaskList = async () => {
    try {
      if (isUpdate && listId) {
        await api.updateTaskList(listId, {
          id: listId,
          nama,
          deskripsi,
          hitung: undefined,
          progress: undefined,
          tugas: undefined,
        });
      } else {
        await api.createTaskList({
          nama,
          deskripsi,
          hitung: undefined,
          progress: undefined,
          tugas: undefined,
        });
      }
      // Setelah berhasil, kembali ke halaman utama
      navigate("/");
    } catch (err) {
      if (axios.isAxiosError(err)) {
        setError(err.response?.data?.message || err.message);
      } else {
        setError("An unknown error occurred");
      }
    }
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    createUpdateTaskList();
  };

  return (
    <div className="p-4 max-w-md mx-auto">
      <div className="flex items-center space-x-4 mb-6">
        <Button onClick={() => navigate("/")}>
          <ArrowLeft size={20} />
        </Button>
        <h1 className="text-2xl font-bold">
          {isUpdate ? "Update Task List" : "Create Task List"}
        </h1>
      </div>
      {error.length > 0 && <Card className="p-4 mb-4 text-red-500">{error}</Card>}
      <form onSubmit={handleSubmit}>
        <Input
          label="Nama"
          placeholder="Enter task list name"
          value={nama}
          onChange={(e) => setNama(e.target.value)}
          required
          fullWidth
        />
        <Spacer y={1} />
        <Textarea
          label="Deskripsi"
          placeholder="Enter task list description (optional)"
          value={deskripsi}
          onChange={(e) => setDeskripsi(e.target.value)}
          fullWidth
        />
        <Spacer y={1} />
        <Button type="submit" color="primary" fullWidth>
          {isUpdate ? "Update Task List" : "Create Task List"}
        </Button>
      </form>
    </div>
  );
};

export default CreateUpdateTaskListScreen;