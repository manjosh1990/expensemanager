import React from 'react'
import styled from 'styled-components'
import { calender, circle, comment, rupee, entertainment, food, freelance, groceries, loan, money, mutual_funds, rent, shopping, stocks, transportation, trash } from '../../utils/icons';
import Button from '../Button/Button';
import { dateFormat } from '../../utils/dateFormat';

const Transaction = ({
  id,
  amount,
  transactionDate,
  category,
  description,
  type,
  indicatorColor,
  deleteItem

}) => {

  const categoryIcon = () => {
    switch (category) {
      case 'SALARY':
        return money;
      case 'freeLancing':
        return freelance;
      case 'STOCKS':
        return stocks;
      case 'FOOD':
        return food;
      case 'RENT':
        return rent;
      case 'GROCERIES':
        return groceries;
      case 'TRANSPORTATION':
        return transportation;
      case 'ENTERTAINMENT':
        return entertainment;
      case 'SHOPPING':
        return shopping;
      case 'MUTUAL_FUNDS':
        return mutual_funds;
      case 'LOAN':
        return loan;
      default:
        return circle;
    }
  }


  return (
    <TransactionStyled indicator={indicatorColor}>
      <div className="icon">
        {categoryIcon()}
      </div>
      <div className="content">
        <h5>{category}</h5>
        <div className="inner-content">
          <div className="text">
            <p>{rupee} {amount}</p>
            <p>{calender} {dateFormat(transactionDate)}</p>
            <p>
              {comment}
              {description}
            </p>
          </div>
          <div className="btn-container">
            <Button
              icon={trash}
              bPad={'1rem'}
              bRad={'50%'}
              bg={'var(--primary-color'}
              color={'#fff'}
              iColor={'#fff'}
              hColor={'var(--color-green)'}
              onClick={() => deleteItem(type,id)}
            />
          </div>
        </div>
      </div>
    </TransactionStyled>
  )
}
const TransactionStyled = styled.div`
    background:#FCF6F9;
    border:2px solid #FFFFFF;
    box-shadow: 0px 1px 15px rgba(0,0,0,0.06);
    border-radius: 20px;
    padding: 1rem;
    margin-bottom: 1rem;
    display: flex;
    align-items: center;
    gap: 1rem;
    width: 100%;
    color:#222260;
    .icon{
        width: 80px;
        height: 80px;
        border-radius: 20px;
        background: #F5F5F5;
        display: flex;
        align-items: center;
        justify-content: center;
        border:2px solid #FFFFFF;
        i{
          font-size: 2.6rem;
        }
    }

    .content{
      flex: 1;
      display: flex;
      flex-direction: column;
      gap:2 rem;
      h5{
        font-size: 1.3rem;
        padding-left: 2rem;
        position: relative;
        &::before{
          content: '';
          position:absolute;
          left:0;
          top:50%;
          transform: translateY(-50%);
          width: .8rem;
          height: .8rem;
          border-radius: 50%;
          background-color: ${props => props.indicator};
        }
      }

      .inner-content{
        display: flex;
        justify-content: space-between;
        align-items: center;
        .text{
          display: flex;
          align-items: center;
          gap:1.5rem;
          p{
            display: flex;
            align-items: center;
            gap: 0.5rem;
            color: var(--primary-color);
            opacity: 0.8;
          }
        }
      }
    }

`;
export default Transaction