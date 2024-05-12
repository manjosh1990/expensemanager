import React, { useState, useContext } from "react";
import axios from "axios";
import { dateFormat } from "../utils/dateFormat";

const BASE_URL = "http://localhost:8080/api/expense/";

const GlobalContext = React.createContext();

export const GlobalProvider = ({ children }) => {
  const [dashboardData, setDashBoardData] = useState(null);
  const [transactions, setTransactions] = useState([]);
  const [error, setError] = useState(null);
  const [categories, setCategories] = useState([]);
  const [total, setTotal] = useState(0);
  const [loading, setLoading] = useState(true);
  const [allTransactions,setAllTransactions] = useState(null)


  //chart states
  const [dates, setDates] = useState([]);
  const [incomes, setIncomes] = useState([]);
  const [expenses, setExpenses] = useState([]);
  const [investments, setInvestments] = useState([]);

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
    getDashboardData();
  };



  const getDashboardData = async () => {
    try {
      const response = await axios.get(`${BASE_URL}transactions/stats`);
      setDashBoardData(response.data);
      if(response.data){
        setDates(response.data.dates);
        setIncomes(response.data.incomesSum)
        setExpenses(response.data.expensesSum)
        setInvestments(response.data.investmentsSum)
      }
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

  const getAllTransactions = async(page,pageSize) =>{
    try{
      setLoading(true);
      pageSize = pageSize?pageSize : 10;
      const response = await axios.get(`${BASE_URL}transactions?page=${page}&pageSize=${pageSize}`);
      setAllTransactions(response.data)
      setLoading(false);
    }catch(error){
      console.log(error)
      setError(error)

    }
  }

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
    getDashboardData();
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

 

  return (
    <GlobalContext.Provider
      value={{
        addTransaction,
        getCategories,
        categories,
        deleteTransaction,
        error,
        setError,
        getTransactionsByType,
        transactions,
        getTransactionSum,
        total,
        loading,
        getDashboardData,
        dashboardData,
        dates,
        incomes,
        expenses,
        investments,
        getAllTransactions,
        allTransactions
      }}
    >
      {children}
    </GlobalContext.Provider>
  );
};

export const useGlobalContext = () => {
  return useContext(GlobalContext);
};
