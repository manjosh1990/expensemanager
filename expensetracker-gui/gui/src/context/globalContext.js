import React, { useState, useContext } from "react";
import axios from "axios";
import { dateFormat } from "../utils/dateFormat";

const BASE_URL = "http://localhost:8080/api/expense/";

const GlobalContext = React.createContext();

export const GlobalProvider = ({ children }) => {
  // const [incomes, setIncomes] = useState([]);
  const [expenses, setExpenses] = useState([]);
  const [dashboardData, setDashBoardData] = useState(null);
  const [transactions, setTransactions] = useState([]);
  const [error, setError] = useState(null);
  const [categories, setCategories] = useState([]);
  const [total, setTotal] = useState(0);
  const [loading, setLoading] = useState(true);
  const addTransaction = async (request, type) => {
    const { transactionDate } = request;
    request = { ...request, transactionDate: dateFormat(transactionDate) };
    console.log(request);
    try {
      const response = await axios.post(`${BASE_URL}transactions`, request);
      console.log(response);
      setLoading(false);
    } catch (err) {
      console.log(err);
      setError(err.response.data);
    }

    getTransactionsByType(type);
  };

  // const getIncomes = async () => {
  //   try {
  //     const response = await axios.get(`${BASE_URL}transactions?type=INCOME`);
  //     setIncomes(response.data.data);
  //   } catch (error) {
  //     console.log("error while fetching incomes " + error);
  //     setError(error);
  //     setIncomes([]);
  //   }
  // };

  const getExpenses = async () => {
    try {
      const response = await axios.get(`${BASE_URL}transactions?type=EXPENSE`);
      setExpenses(response.data.data);
    } catch (error) {
      console.log("error while fetching expenses " + error);
      setError(error);
      setExpenses([]);
    }
  };

  const getDashboardData = async () => {
    try {
      const response = await axios.get(`${BASE_URL}transactions/stats`);
      setDashBoardData(response.data);
    } catch (error) {
      console.log("error while fetching dashboard data " + error);
      setError(error);
    }
  };

  const getTransactionsByType = async (type) => {
    try {
      setLoading(true);
      const response = await axios.get(`${BASE_URL}transactions?type=${type}`);
      setTransactions(response.data.data);
      getTransactionSum(type);
    } catch (error) {
      console.log("error while fetching expenses " + error);
      setError(error);
      setTransactions([]);
    }
  };

  const getCategories = async () => {
    try {
      const response = await axios.get(`${BASE_URL}transactions/category`);
      setCategories(response.data);
    } catch (error) {
      console.log("error while fetching categories ," + error);
    }
  };
  const deleteTransaction = async (type, id) => {
    try {
      const res = await axios.delete(`${BASE_URL}transactions/${id}`);
      console.log(res);
      setLoading(false);
    } catch (error) {
      console.log("error while deleting transaction");
    }
    getTransactionsByType(type);
  };

  const getTransactionSum = async (type) => {
    try {
      const res = await axios(`${BASE_URL}transactions/sum/${type}`);
      setLoading(false);
      setTotal(res.data);
    } catch (error) {
      console.log(error);
    }
  };

  // const transactionHistory = () => {
  //   //call backend
  //   const history = [...incomes, ...expenses];
  //   history.sort((a, b) => {
  //     return new Date(b.transactionDate) - new Date(a.transactionDate);
  //   });
  //   return history.slice(0, 5);
  // };
  return (
    <GlobalContext.Provider
      value={{
        addTransaction,
        getCategories,
        categories,
        deleteTransaction,
        expenses,
        getExpenses,
        error,
        setError,
        getTransactionsByType,
        transactions,
        getTransactionSum,
        total,
        loading,
        getDashboardData,
        dashboardData
      }}
    >
      {children}
    </GlobalContext.Provider>
  );
};

export const useGlobalContext = () => {
  return useContext(GlobalContext);
};
