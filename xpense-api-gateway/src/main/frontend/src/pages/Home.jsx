import React,{useEffect,useState} from 'react'
import { TbCurrencyRupee } from 'react-icons/tb';
import { GoPrimitiveDot } from 'react-icons/go';
import { Stacked, Pie, Button, SparkLine } from '../components';
import { expenses, SparklineAreaData, ecomPieChartData } from '../data/dummy';
import Service  from '../HttpUtil';

import { useStateContext } from '../contexts/ContextProvider';
import { itemMove } from '@syncfusion/ej2/treemap';


const Home = () => {
  const[netWorth, setNetWorth] = useState(0);
  const[foodExpense,setFoodExpense] = useState(0);
  const httpService = new Service();
  //used to format number
  const numberFormat = (value) =>
    new Intl.NumberFormat('en-IN', {
      style: 'currency',
      currency: 'INR'
    }).format(value);
  
    //fetch networth from backend
    useEffect(()=>{
      setTimeout(()=>{
        async function getNetWorth(){
          try{
            const res = await httpService.get("getNetWorth");
            console.log("data",res)
            setNetWorth(res.balance)
          }catch(error){
            console.log("could not fetch netWorth: exception occured: "+ error);
          }
        }

        //fetch food expense
        async function getFoodExpenses(){
          try{
          const res = await httpService.get("foodExpense");
          console.log("food",res);
          setFoodExpense(res.expense)
          }catch(error){
            console.log("could not fetch foodExpense: exception occured: "+ error);
          }
        }
        getNetWorth();
        getFoodExpenses();
      },2000)
     
    },[]);


  return (
    <div className='mt-12'>
      <div className='flex flex-wrap lg:flex-nowrap justify-center '>
        <div className='bg-white dark:text-gray-200 dark:bg-secondary-dark-bg h-44 rounded-xl w-full lg:w-80 p-8 pt-9 m-3 bg-hero-pattern bg-no-repeat bg-cover bg-center'>
          <div className='flex justify-between items-center'>
            <div>
              <p className='font-bold text-gray-400'>NET WORTH:</p>
              <p className='text-2xl'>{numberFormat(netWorth)}</p>
            </div>
          </div>
          <div className='mt-6'>
            < Button
              color="white"
              bgColor="blue"
              text="Download"
              borderRadius="10px"
              size ="md"
            />
          </div>
        </div>
        <div className='flex m-3 flex-wrap justify-center gap-1 items-center'>
          {expenses.map(
            (item)=>(
              <div key={item.title}
              className="bg-white dark:text-gray-200 dark:bg-secondary-dark-bg md:w-56 p-4 pt-9 rounded-2xl">
                <button type="button"
                style={{color:item.iconColor, backgroundColor:item.iconBg}} 
                
                className="text-2xl opacity-0.9 rounded-full p-4 hover:drop-shadow-xl">
                  {item.icon}
                  </button> 
                  <p className='mt-3'>
                      <span className='text-lg font-semibold'>
                        {item.name === "food"?numberFormat(foodExpense) : item.amount}
                      </span>
                      <span className={`text-sm text-${item.pcColor} ml-2`}>
                        {item.percentage}
                      </span>
                  </p>
                  <p className='text-sm text-gray-400 mt-1'>{item.title}</p>
              </div>
            )
          )}
        </div>
      </div>
    </div>
  )
}

export default Home