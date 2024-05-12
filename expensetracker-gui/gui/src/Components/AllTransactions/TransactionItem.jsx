import React from 'react'
import styled from 'styled-components'
import Button from '../Button/Button';
import { trash } from '../../utils/icons';

const TransactionItem = ({ id, amount, transactionDate, type, category, description, createdAt }) => {
  return (
    <TransactionItemStyled>
      <div className="table-con">
        <div className="table-row">
          <div className='date-r'><p>{transactionDate}</p></div>
          <div className='category-r'><p>{category}</p></div>
          <div className='desc-r'><p>{description}</p></div>
          <div className='type-r'><p>{type}</p></div>
          <div className='amount-r'><p style={{
            color: type === 'EXPENSE' ? 'red' : 'var(--color-green)'
          }}>{amount}</p></div>
          <div className='action-r'>
            <Button name="" icon={trash} />
          </div>
        </div>
      </div>
    </TransactionItemStyled>
  )
}

const TransactionItemStyled = styled.div`
.table-con{
  width: 100%;
  margin-left: 10px;
  margin-right: 10px;
  margin-top: 10px;
  padding-left: 20px;
  padding-right: 20px;
  height: 30px;
  box-shadow: 0px 1px 15px rgba(0,0,0,0.06);
  border-radius: .5rem;
}
.table-row{
  width: 100%;
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  
  p{
    text-align: center;
    width: 100%;
  }
}
.date-r,.category-r,.type-r,.amount-r,.action-r{
    width: 15%;
    align-items: center;
    }
.desc-r{
   width: 25%;
   }
.category-r{
    width:15%;
   }
   .action-r{
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
   }
`;

export default TransactionItem