import React, { useEffect } from 'react'
import styled from 'styled-components'
import { InnerLayout } from '../../styles/Layout';
import { useGlobalContext } from '../../context/globalContext';
import Form from '../Forms/Form';
import Transaction from '../Transaction/Transaction';

const Expenses = () => {
  const {expenses,getExpenses,totalExpense,deleteTransaction} = useGlobalContext();
  useEffect(()=>{
    getExpenses();
  },[])
  return (
    <ExpensesStyled>
      <InnerLayout>
      <h1>Expenses</h1>
      <h2 className='total-expense'>Total expenses for this month:
        <span>₹{totalExpense()}</span>
        </h2>
        <div className="expense-content">
            <div className="form-container">
              <Form formType="EXPENSE"/>
            </div>
            <div className="expenses">
              {
                expenses.map((expense)=>{
                  const {id,amount,transactionDate,type,category,description,createAt} = expense;
                  return<Transaction key={id} 
                      id ={id}
                      amount={amount}
                      transactionDate= {transactionDate}
                      type= {type}
                      category= {category}
                      description= {description}
                      createAt= {createAt}
                      indicatorColor="var(--color-green)"
                      deleteItem={deleteTransaction}
                  />
                })
              }
            </div>
        </div>
      </InnerLayout>
    </ExpensesStyled>
  )
}

const ExpensesStyled = styled.div`
  display: flex;
  overflow: auto;
  .total-expense{
    display: flex;
    justify-content: center;
    align-items: center;
    background: #FCF6F9;
    border: 2px solid #FFFFFF;
    box-shadow: 0px 1px 15px rgba(0,0,0,0.06);
    border-radius: 20px;
    padding: 1rem;
    font-size: 2rem;
    margin: 1rem 0;
    gap: 0.5rem;
    span{
      font-size:2.5rem;
      font-weight: 800;
      color: var(--color-green);
    }
  }
  .expense-content{
    display: flex;
    gap: 2rem;
    .expenses{
      flex:1;
    }
  }
`;


export default Expenses