import React,{useEffect} from 'react'
import styled from 'styled-components'
import { InnerLayout } from '../../styles/Layout';
import Form from '../Forms/Form';
import { useGlobalContext } from '../../context/globalContext';
import Transaction from '../Transaction/Transaction';
import { capitalizeFirstLetter } from '../../utils/stringUtils';

const Incomes = ({type}) => {
const {incomes,getIncomes,totalIncome,deleteTransaction,getInvestments,investments,totalInvestments,getTransactionsByType,transactions} = useGlobalContext();

useEffect(()=>{
  if(type === 'INVESTMENT'){
    getTransactionsByType(type);
  }else
    getIncomes();
  },[])

  const getItemArr = ()=>{
    let arr = incomes;
    if(type === "INVESTMENT"){
      arr = transactions;
    }
    return arr;
  }
  return (
    <IncomeStyled>
      <InnerLayout>
        <h1>{capitalizeFirstLetter(type)}</h1>
        <h2 className='total-income'>Total {capitalizeFirstLetter(type)} for this month:
        <span>₹{type==="INCOME"?totalIncome():totalInvestments()}</span>
        </h2>
        <div className="income-content">
            <div className="form-container">
              <Form formType={type}/>
            </div>
            <div className="incomes">
              {
                getItemArr().map((income)=>{
                  const {id,amount,transactionDate,type,category,description,createAt} = income;
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
    </IncomeStyled>
  )
}

const IncomeStyled = styled.div`
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


export default Incomes