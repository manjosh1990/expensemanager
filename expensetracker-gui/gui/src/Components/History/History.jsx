import React from 'react'
import styled from 'styled-components'
import { useGlobalContext } from '../../context/globalContext'

const History = () => {
  const {transactionHistory} = useGlobalContext();
  const[...history] = transactionHistory();
  return (
    <HistoryStyled>
      <h2>Recent History</h2>
      {
        history.map((item)=>{
          const {id,type,amount,description} = item
          return (
            <div className="history-item" key ={id}>
                <p style ={{
                  color : type ==='EXPENSE' ? 'red' : 'var(--color-green)'
                }}>
                  {description}
                </p>
                <p style ={{
                  color : type ==='EXPENSE' ? 'red' : 'var(--color-green)'
                }}>
                  {
                    type === 'EXPENSE' ? `-${amount<0?0:amount}` : `+${amount<0?0:amount}`
                  }
                </p>
            </div>
          )
        })
      }
    </HistoryStyled>
  )
}

const HistoryStyled = styled.div`
display: flex;
    flex-direction: column;
    gap: 1rem;
    .history-item{
        background: #FCF6F9;
        border: 2px solid #FFFFFF;
        box-shadow: 0px 1px 15px rgba(0, 0, 0, 0.06);
        padding: 1rem;
        border-radius: 20px;
        display: flex;
        justify-content: space-between;
        align-items: center;
    }
`;

export default History