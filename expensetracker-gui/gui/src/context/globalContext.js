import React, { useState, useContext } from "react";
import axios from "axios";
import { dateFormat } from "../utils/dateFormat";

const BASE_URL = "http://localhost:8080/api/expense/";

const GlobalContext = React.createContext();

export const GlobalProvider = ({ children }) => {
  const [incomes, setIncomes] = useState([]);
  const [expenses, setExpenses] = useState([]);
  const [investments, setInvestments] = useState([]);
  const [transactions, setTransactions] = useState([]);
  const [error, setError] = useState(null);
  const [categories, setCategories] = useState([]);
  const[total,setTotal]= useState(0);

  const addTransaction = async (request) => {
    const { transactionDate } = request;
    request = { ...request, transactionDate: dateFormat(transactionDate) };
    console.log(request);
    try {
      const response = await axios.post(`${BASE_URL}transactions`, request);
      console.log(response);
    } catch (err) {
      console.log(err);
      setError(err.response.data);
    }

    if (request.type === "INCOME") {
      getIncomes();
    } else if (request.type === "EXPENSE") {
      getExpenses();
    }
  };

  const getIncomes = async () => {
    try {
      const response = await axios.get(`${BASE_URL}transactions?type=INCOME`);
      setIncomes(response.data.data);
    } catch (error) {
      console.log("error while fetching incomes " + error);
      setError(error);
      setIncomes([]);
    }
  };

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

  const getInvestments = async () => {
    try {
      const response = await axios.get(`${BASE_URL}transactions?type=INVESTMENT`);
      setInvestments(response.data.data);
    } catch (error) {
      console.log("error while fetching expenses " + error);
      setError(error);
      setInvestments([]);
    }
  };

  const getTransactionsByType = async (type) => {
    try {
      const response = await axios.get(`${BASE_URL}transactions?type=${type}`);
      setTransactions(response.data.data);
    } catch (error) {
      console.log("error while fetching expenses " + error);
      setError(error);
      setTransactions([]);
    }
  };

  const totalIncome = () => {
    let totalIncome = 0;
    incomes.forEach((income) => {
      totalIncome += income.amount;
    });
    return totalIncome;
  };

  const totalInvestments = () => {
    let totalInvestments = 0;
    investments.forEach((income) => {
      totalInvestments += income.amount;
    });
    return totalInvestments;
  };

  const totalExpense = () => {
    let totalExpense = 0;
    expenses.forEach((expense) => {
      totalExpense += expense.amount;
    });
    return totalExpense;
  };

  const getCategories = async () => {
    try {
      const response = await axios.get(`${BASE_URL}transactions/category`);
      setCategories(response.data);
    } catch (error) {
      console.log("error while fetching categories ," + error);
    }
  };
  const deleteTransaction = async (id) => {
    try {
      const res = await axios.delete(`${BASE_URL}transactions/${id}`);
      console.log(res);
    } catch (error) {
      console.log("error while deleting transaction");
    }
    getIncomes();
    getExpenses();
  };

  const totalBalance = () => { //call backend
    return totalIncome() - totalExpense();
  };


  const getTransactionSum = async (type) => {
    
    try{
      const res = await axios (`${BASE_URL}transactions/sum/${type}`)
      setTotal(res.data);
    }catch(error){
      console.log(error)
    }
  };


  const transactionHistory = () => { //call backend
    const history = [...incomes, ...expenses];
    history.sort((a, b) => {
      return new Date(b.transactionDate) - new Date(a.transactionDate);
    });
    return history.slice(0, 5);
  };
  return (
    <GlobalContext.Provider
      value={{
        addTransaction,
        incomes,
        getIncomes,
        totalIncome,
        getCategories,
        categories,
        deleteTransaction,
        expenses,
        getExpenses,
        totalExpense,
        totalBalance,
        transactionHistory,
        error,
        setError,
        getInvestments,
        investments,
        totalInvestments,
        getTransactionsByType,
        transactions,
        getTransactionSum,
        total
      }}
    >
      {children}
    </GlobalContext.Provider>
  );
};

export const useGlobalContext = () => {
  return useContext(GlobalContext);
};
