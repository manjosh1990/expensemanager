import React, { useEffect, useState } from 'react'
import styled from 'styled-components'
import { useGlobalContext } from '../../context/globalContext'
import TransactionItem from './TransactionItem';
import Button from '../Button/Button';
import { next, prev } from '../../utils/icons';

const AllTransactions = () => {

  const { getAllTransactions, allTransactions } = useGlobalContext();
  const [pageNo, setPageNo] = useState(1);
  const [isLoading, setIsLoading] = useState(true);
  const [noRows,setNowRows]= useState(10);

  useEffect(() => {
    getAllTransactions(pageNo,noRows).finally(() => setIsLoading(false));;
  }, [pageNo,noRows])
  
  const handleNoOfRows =(e)=>{
    const value = e.target.value;
    setNowRows(value);
  }
  const callNextPage = ()=>{
    let currentPage = allTransactions?allTransactions.currentPage : 0;
    setPageNo(currentPage+1);
  }

  const callPrevPage = ()=>{
    let currentPage = allTransactions?allTransactions.currentPage : 0;
    setPageNo(currentPage-1);
  }
  const renderTransactionItems = () => {
    const elements = allTransactions && allTransactions.data.map((transaction) => {
      const { id, amount, transactionDate, type, category, description, createAt } = transaction;

      return (<TransactionItem
        key={id}
        amount={amount}
        transactionDate={transactionDate}
        type={type}
        category={category}
        description={description}
        createAt={createAt}
      />)
    })
    return (<>{elements}</>)
  }
  return (
    <AllTransactionStyled>
      <h2>
        Transactions
      </h2>
      {isLoading ? <p>Loading...</p> : <>
        <div className='control-container'>
          <div className='page-nav'>
            {allTransactions && allTransactions.hasPrevious ? <Button
            icon={prev}
            onClick={() => callPrevPage()}
            name={`Prev`}
            bg={'#FCF6F9'}
            iColor={'##222260'}
              color={'#222260'}
            /> : <Button
              icon={prev}
              color={'#22226099'}
              bg={'#FCF6F9'}
              name={`Prev`}
            />}
            <span><h5>Page {allTransactions && allTransactions.currentPage} of {allTransactions && allTransactions.totalPages}</h5></span>
            {allTransactions && allTransactions.hasNext ? <Button
              icon={next}
              onClick={() => callNextPage()}  
              name={`Next`}
              bg={'#FCF6F9'}
              iColor={'##222260'}
              color={'#222260'}
            /> : <Button
            name={`Next`}
            icon={next}
            color={'#22226099'}
            iColor={'#22226099'}
            bg={'#FCF6F9'}
          />}
          <div className='records-per-row'>
          <h4>Records per page:</h4>
          <select name="noRows" value ={noRows} onChange={(e)=>handleNoOfRows(e)}>
            <option value={10}>10</option>
            <option value={15}>15</option>
            <option value={30}>30</option>
            <option value={50}>50</option>
            <option value={100}>100</option>
          </select>
          </div>
          </div>
          <div className='search-con'>
            search bar
          </div>
        </div>
        <div className='item-container'>
          <div className='table-title'>
              <div className='date'>Date</div>
              <div className='category'>Category</div>
              <div className='desc'>Description</div>
              <div className='type'>Type</div>
              <div className='amount'>Amount</div>
              <div className='action'>Action</div>
          </div>
          {renderTransactionItems()}
        </div>
      </>}
    </AllTransactionStyled>
  )
}
const AllTransactionStyled = styled.div`
display: flex;
flex-direction: column;
align-items: center;
margin-top: 1.5rem;
.control-container{
  display: flex;
  width: 85%;
  margin:1.2rem;
  height:2.5rem;
  box-shadow: 0px 1px 15px rgba(0,0,0,0.06);
  border-radius: 1.1rem;
  background-color: #FCF6F9;
}
.page-nav{
  display: flex;
  gap: 2rem;
  width: 50%;
  flex-wrap: wrap;
  align-items: center;
  justify-content: flex-start;
  padding: 0 1.5rem;
}
.item-container{
  background-color: #FCF6F9;
  box-shadow: 0px 1px 15px rgba(0,0,0,0.06);
  border-radius: 1rem;
  color: #222260;
  width: 85%;
  padding: 10px; 
}
.table-title {
    background-color: #222260;
    height: 45px;
    width: 100%;
    border-radius: 1rem;
    margin-top: 10px;
    padding-left: 20px;
    padding-right: 20px;
    display: flex;
    flex-wrap: wrap;
    align-items: center;
  }
  .date,.category,.desc,.type,.amount,.action{
      color: #FCF6F9;
      text-align: center;
  }
  .date,.category,.type,.amount,.action{
    width: 15%;
    }
    .desc{
    width: 25%;
    }
  .records-per-row{
    display: flex;
    justify-content: flex-end;
    select{
            margin-left: 10px;
            color: rgba(34, 34, 96, 0.4);
            &:focus, &:active{
                color: rgba(34, 34, 96, 1);
            }
            border: none;
        }
  }
`;
export default AllTransactions