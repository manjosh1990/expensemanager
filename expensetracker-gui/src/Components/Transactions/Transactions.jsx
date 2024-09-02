import React, { useEffect } from 'react'
import styled from 'styled-components';
import { useGlobalContext } from '../../context/globalContext';
import { InnerLayout } from '../../styles/Layout';
import { capitalizeFirstLetter } from '../../utils/stringUtils';
import Form from '../Forms/Form';
import Transaction from '../Transaction/Transaction';

const Transactions = ({ type }) => {
  const { deleteTransaction, getTransactionsByType, transactions, total ,loading} = useGlobalContext();
  useEffect(() => {
    getTransactionsByType(type);
  }, [type])
  const renderTransactions = () => {
    const transactionElements = transactions.map((transaction) => {
      console.log(transaction)
      const { id, amount, transactionDate, type, category, description, createAt } = transaction;
      return <Transaction key={id}
        id={id}
        amount={amount}
        transactionDate={transactionDate}
        type={type}
        category={category}
        description={description}
        createAt={createAt}
        indicatorColor="var(--color-green)"
        deleteItem={deleteTransaction}
      />
    })

    return (<>
      {transactionElements}
    </>)
  }
  return (
    <TransactionsStyled>
      <InnerLayout>{
          loading ? (<h1>Loading...</h1>) : <>
          <h1>My {capitalizeFirstLetter(type)}s</h1>
        <h2 className='total-con'>Total {capitalizeFirstLetter(type)}s for this month:
          <span>₹{total}</span>
        </h2>
        <div className='transaction-content'>
          <div>
            <Form formType={type} />
          </div>
          <div className='transactions'>
            {renderTransactions()}
          </div>
        </div>
          </>
        }
        
      </InnerLayout>
    </TransactionsStyled>
  )
}

const TransactionsStyled = styled.div`
  display: flex;
  overflow: auto;
  .total-con{
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
  .transaction-content{
    display: flex;
    gap: 2rem;
    .transactions{
      flex:1;
    }
  }
`;

export default Transactions