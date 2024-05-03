import React, { useEffect } from 'react'
import styled from 'styled-components';
import { useGlobalContext } from '../../context/globalContext';
import { InnerLayout } from '../../styles/Layout';
import { capitalizeFirstLetter } from '../../utils/stringUtils';

const Transactions = ({type}) => {

  const {deleteTransaction,totalInvestments,getTransactionsByType,transactions,getTransactionSum,total} = useGlobalContext();

  useEffect(()=>{
    getTransactionsByType(type);
    getTransactionSum(type);
  },[])

  return (
    <TransactionsStyled>
      <InnerLayout>
      <h1>My {capitalizeFirstLetter(type)}s</h1>
      <h2 className='total-income'>Total {capitalizeFirstLetter(type)}s for this month:
      <span>₹{total}</span>
        </h2>
      </InnerLayout>
    </TransactionsStyled>
  )
}

const TransactionsStyled = styled.div`
  display: flex;
  overflow: auto;
  .total-income{
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
  .income-content{
    display: flex;
    gap: 2rem;
    .incomes{
      flex:1;
    }
  }
`;

export default Transactions