import React, { createContext, useContext, useReducer, useEffect } from "react";
import axios from "axios";
import TugasDaftar from "./domain/TugasDaftar";
import Tugas from "./domain/Tugas";

interface AppState {
  taskLists: TugasDaftar[];
  tasks: { [taskListId: string]: Tugas[] };
}

type Action =
  | { type: "FETCH_TASKLISTS"; payload: TugasDaftar[] }
  | { type: "GET_TASKLIST"; payload: TugasDaftar }
  | { type: "CREATE_TASKLIST"; payload: TugasDaftar }
  | { type: "UPDATE_TASKLIST"; payload: TugasDaftar }
  | { type: "DELETE_TASKLIST"; payload: string }
  | { type: "FETCH_TASKS"; payload: { taskListId: string; tasks: Tugas[] } }
  | { type: "CREATE_TASK"; payload: { taskListId: string; task: Tugas } }
  | { type: "GET_TASK"; payload: { taskListId: string; task: Tugas } }
  | {
      type: "UPDATE_TASK";
      payload: { taskListId: string; taskId: string; task: Tugas };
    }
  | { type: "DELETE_TASK"; payload: { taskListId: string; taskId: string } };

const FETCH_TASKLISTS = "FETCH_TASKLISTS";
const GET_TASKLIST = "GET_TASKLIST";
const CREATE_TASKLIST = "CREATE_TASKLIST";
const UPDATE_TASKLIST = "UPDATE_TASKLIST";
const DELETE_TASKLIST = "DELETE_TASKLIST";
const FETCH_TASKS = "FETCH_TASKS";
const CREATE_TASK = "CREATE_TASK";
const GET_TASK = "GET_TASK";
const UPDATE_TASK = "UPDATE_TASK";
const DELETE_TASK = "DELETE_TASK";

const reducer = (state: AppState, action: Action): AppState => {
  switch (action.type) {
    case FETCH_TASKLISTS:
      return { ...state, taskLists: action.payload };
    case GET_TASKLIST:
      return {
        ...state,
        taskLists: state.taskLists.some((list) => list.id === action.payload.id)
          ? state.taskLists.map((list) =>
              list.id === action.payload.id ? action.payload : list
            )
          : [...state.taskLists, action.payload],
      };
    case CREATE_TASKLIST:
      return { ...state, taskLists: [...state.taskLists, action.payload] };
    case UPDATE_TASKLIST:
      return {
        ...state,
        taskLists: state.taskLists.map((list) =>
          list.id === action.payload.id ? action.payload : list
        ),
      };
    case DELETE_TASKLIST:
      return {
        ...state,
        taskLists: state.taskLists.filter((list) => list.id !== action.payload),
      };
    case FETCH_TASKS:
      return {
        ...state,
        tasks: {
          ...state.tasks,
          [action.payload.taskListId]: action.payload.tasks,
        },
      };
    case CREATE_TASK:
      return {
        ...state,
        tasks: {
          ...state.tasks,
          [action.payload.taskListId]: [
            ...(state.tasks[action.payload.taskListId] || []),
            action.payload.task,
          ],
        },
      };
    case GET_TASK: {
      const existingTasks = state.tasks[action.payload.taskListId] || [];
      const taskExists = existingTasks.some(
        (task) => task.id === action.payload.task.id
      );
      const updatedTasks = taskExists
        ? existingTasks.map((task) =>
            task.id === action.payload.task.id ? action.payload.task : task
          )
        : [...existingTasks, action.payload.task];
      return {
        ...state,
        tasks: {
          ...state.tasks,
          [action.payload.taskListId]: updatedTasks,
        },
      };
    }
    case UPDATE_TASK:
      return {
        ...state,
        tasks: {
          ...state.tasks,
          [action.payload.taskListId]: state.tasks[action.payload.taskListId].map(
            (task) =>
              task.id === action.payload.taskId ? action.payload.task : task
          ),
        },
      };
    case DELETE_TASK:
      return {
        ...state,
        tasks: {
          ...state.tasks,
          [action.payload.taskListId]: state.tasks[
            action.payload.taskListId
          ].filter((task) => task.id !== action.payload.taskId),
        },
      };
    default:
      return state;
  }
};

const initialState: AppState = {
  taskLists: [],
  tasks: {},
};

interface AppContextType {
  state: AppState;
  api: {
    fetchTaskLists: () => Promise<void>;
    getTaskList: (id: string) => Promise<void>;
    createTaskList: (taskList: Omit<TugasDaftar, "id">) => Promise<void>;
    updateTaskList: (id: string, taskList: TugasDaftar) => Promise<void>;
    deleteTaskList: (id: string) => Promise<void>;
    fetchTasks: (taskListId: string) => Promise<void>;
    createTask: (
      taskListId: string,
      task: Omit<Tugas, "id" | "taskListId">
    ) => Promise<void>;
    getTask: (taskListId: string, taskId: string) => Promise<void>;
    updateTask: (
      taskListId: string,
      taskId: string,
      task: Tugas
    ) => Promise<void>;
    deleteTask: (taskListId: string, taskId: string) => Promise<void>;
  };
}

const AppContext = createContext<AppContextType | undefined>(undefined);

export const AppProvider: React.FC<{ children: React.ReactNode }> = ({
  children,
}) => {
  const [state, dispatch] = useReducer(reducer, initialState);

  const jsonHeaders = { headers: { "Content-Type": "application/json" } };

  const api: AppContextType["api"] = {
    fetchTaskLists: async () => {
      const response = await axios.get<TugasDaftar[]>("/api/task-lists", jsonHeaders);
      dispatch({ type: FETCH_TASKLISTS, payload: response.data });
    },
    getTaskList: async (id: string) => {
      const response = await axios.get<TugasDaftar>(`/api/task-lists/${id}`, jsonHeaders);
      dispatch({ type: GET_TASKLIST, payload: response.data });
    },
    createTaskList: async (taskList) => {
      const response = await axios.post<TugasDaftar>("/api/task-lists", taskList, jsonHeaders);
      dispatch({ type: CREATE_TASKLIST, payload: response.data });
    },
    updateTaskList: async (id, taskList) => {
      const response = await axios.put<TugasDaftar>(`/api/task-lists/${id}`, taskList, jsonHeaders);
      dispatch({ type: UPDATE_TASKLIST, payload: response.data });
    },
    deleteTaskList: async (id) => {
      await axios.delete(`/api/task-lists/${id}`, jsonHeaders);
      dispatch({ type: DELETE_TASKLIST, payload: id });
    },
    fetchTasks: async (taskListId) => {
      const response = await axios.get<Tugas[]>(
        `/api/task-lists/${taskListId}/tasks`,
        jsonHeaders
      );
      dispatch({
        type: FETCH_TASKS,
        payload: { taskListId, tasks: response.data },
      });
    },
    createTask: async (taskListId, task) => {
      const response = await axios.post<Tugas>(
        `/api/task-lists/${taskListId}/tasks`,
        task,
        jsonHeaders
      );
      dispatch({
        type: CREATE_TASK,
        payload: { taskListId, task: response.data },
      });
    },
    getTask: async (taskListId, taskId) => {
      const response = await axios.get<Tugas>(
        `/api/task-lists/${taskListId}/tasks/${taskId}`,
        jsonHeaders
      );
      dispatch({
        type: GET_TASK,
        payload: { taskListId, task: response.data },
      });
    },
    updateTask: async (taskListId, taskId, task) => {
      const response = await axios.put<Tugas>(
        `/api/task-lists/${taskListId}/tasks/${taskId}`,
        task,
        jsonHeaders
      );
      dispatch({
        type: UPDATE_TASK,
        payload: { taskListId, taskId, task: response.data },
      });
    },
    deleteTask: async (taskListId, taskId) => {
      await axios.delete(`/api/task-lists/${taskListId}/tasks/${taskId}`, jsonHeaders);
      dispatch({ type: DELETE_TASK, payload: { taskListId, taskId } });
    },
  };

  useEffect(() => {
    api.fetchTaskLists();
  }, []);

  return (
    <AppContext.Provider value={{ state, api }}>
      {children}
    </AppContext.Provider>
  );
};

export const useAppContext = (): AppContextType => {
  const context = useContext(AppContext);
  if (context === undefined) {
    throw new Error("useAppContext must be used within an AppProvider");
  }
  return context;
};