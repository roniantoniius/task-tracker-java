import { parseDate } from "@internationalized/date";
import {
  Button,
  Checkbox,
  DateInput,
  Progress,
  Spacer,
  Table,
  TableBody,
  TableCell,
  TableColumn,
  TableHeader,
  TableRow,
  Spinner,
} from "@nextui-org/react";
import { ArrowLeft, Edit, Minus, Plus, Trash } from "lucide-react";
import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { useAppContext } from "../AppProvider";
import Tugas from "../domain/Tugas";
import { TugasStatus } from "../domain/TugasStatus";

const TasksScreen: React.FC = () => {
  const { state, api } = useAppContext();
  const { listId } = useParams<{ listId: string }>();
  const [isLoading, setIsLoading] = useState(true);
  const navigate = useNavigate();

  // Cari task list langsung dari state
  const taskList = state.taskLists.find((tl) => tl.id === listId);

  useEffect(() => {
    const loadInitialData = async () => {
      if (!listId) return;
      setIsLoading(true);
      try {
        if (!taskList) {
          await api.getTaskList(listId);
        }
        try {
          await api.fetchTasks(listId);
        } catch (error) {
          console.log("Tasks not available yet");
        }
      } catch (error) {
        console.error("Error loading task list:", error);
      } finally {
        setIsLoading(false);
      }
    };

    loadInitialData();
  }, [listId]);

  // Hitung persentase penyelesaian task
  const completionPercentage = React.useMemo(() => {
    if (listId && state.tasks[listId]) {
      const tasks = state.tasks[listId];
      const closedTaskCount = tasks.filter(
        (task) => task.status === TugasStatus.TUTUP
      ).length;
      return tasks.length > 0 ? (closedTaskCount / tasks.length) * 100 : 0;
    }
    return 0;
  }, [state.tasks, listId]);

  const toggleStatus = (task: Tugas) => {
    if (listId) {
      const updatedTask = { ...task };
      updatedTask.status =
        task.status === TugasStatus.TUTUP ? TugasStatus.BUKA : TugasStatus.TUTUP;

      api
        .updateTask(listId, task.id!, updatedTask)
        .then(() => api.fetchTasks(listId));
    }
  };

  const deleteTaskList = async () => {
    if (listId) {
      await api.deleteTaskList(listId);
      navigate("/");
    }
  };

  const tableRows = () => {
    if (listId && state.tasks[listId]) {
      return state.tasks[listId].map((task) => (
        <TableRow key={task.id}>
          <TableCell className="px-4 py-2">
            <Checkbox
              isSelected={task.status === TugasStatus.TUTUP}
              onValueChange={() => toggleStatus(task)}
              aria-label={`Mark task "${task.nama}" as ${
                task.status === TugasStatus.TUTUP ? "open" : "closed"
              }`}
            />
          </TableCell>
          <TableCell className="px-4 py-2">{task.nama}</TableCell>
          <TableCell className="px-4 py-2">{task.prioritas}</TableCell>
          <TableCell className="px-4 py-2">
            {task.waktuDeadline && (
              <DateInput
                isDisabled
                defaultValue={parseDate(
                  new Date(task.waktuDeadline).toISOString().split("T")[0]
                )}
                aria-label={`Due date for task "${task.nama}"`}
              />
            )}
          </TableCell>
          <TableCell className="px-4 py-2">
            <div className="flex space-x-2">
              <Button
                variant="ghost"
                aria-label={`Edit task "${task.nama}"`}
                onClick={() =>
                  navigate(`/task-lists/${listId}/edit-task/${task.id!}`)
                }
              >
                <Edit className="h-4 w-4" />
              </Button>
              <Button
                variant="ghost"
                onClick={() => api.deleteTask(listId, task.id!)}
                aria-label={`Delete task "${task.nama}"`}
              >
                <Trash className="h-4 w-4" />
              </Button>
            </div>
          </TableCell>
        </TableRow>
      ));
    } else {
      return [];
    }
  };

  if (isLoading) {
    return <Spinner />;
  }

  return (
    <div className="p-4 max-w-4xl mx-auto">
      <div className="flex items-center justify-between mb-6">
        <div className="flex w-full items-center justify-between">
          <Button
            variant="ghost"
            aria-label="Go back to Task Lists"
            onClick={() => navigate("/")}
          >
            <ArrowLeft className="h-4 w-4" />
          </Button>

          <h1 className="text-2xl font-bold mx-4">
            {taskList ? taskList.nama : "Unknown Task List"}
          </h1>

          <Button
            variant="ghost"
            aria-label="Edit task list"
            onClick={() => navigate(`/edit-task-list/${listId}`)}
          >
            <Edit className="h-4 w-4" />
          </Button>
        </div>
      </div>

      <Progress
        value={completionPercentage}
        className="mb-4"
        aria-label="Task completion progress"
      />
      <Button
        onClick={() => navigate(`/task-lists/${listId}/new-task`)}
        aria-label="Add new task"
        className="mb-4 w-full"
      >
        <Plus className="h-4 w-4" /> Add Task
      </Button>
      <div className="border rounded-lg overflow-hidden">
        <Table className="w-full" aria-label="Tasks list">
          <TableHeader>
            <TableColumn>Completed</TableColumn>
            <TableColumn>Title</TableColumn>
            <TableColumn>Priority</TableColumn>
            <TableColumn>Due Date</TableColumn>
            <TableColumn>Actions</TableColumn>
          </TableHeader>
          <TableBody>{tableRows()}</TableBody>
        </Table>
      </div>
      <Spacer y={4} />
      <div className="flex justify-end">
        <Button
          color="danger"
          startContent={<Minus size={20} />}
          onClick={deleteTaskList}
          aria-label="Delete current task list"
        >
          Delete TaskList
        </Button>
      </div>

      <Spacer y={4} />
    </div>
  );
};

export default TasksScreen;