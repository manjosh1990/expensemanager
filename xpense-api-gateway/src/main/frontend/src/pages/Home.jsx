import React, { useEffect, useState } from 'react'
import { TbCurrencyRupee } from 'react-icons/tb';
import { GoPrimitiveDot } from 'react-icons/go';
import { Stacked, Pie, Button, SparkLine } from '../components';
import { expenses, SparklineAreaData, ecomPieChartData } from '../data/dummy';
import Service from '../HttpUtil';

import { useStateContext } from '../contexts/ContextProvider';
import { itemMove } from '@syncfusion/ej2/treemap';
import { data } from 'autoprefixer';


const Home = () => {
  console.log("loading dashboard")
  const [dashboardData, setdashboardData] = useState({
    netWorth:0,
    food: 0,
    groceries: 0,
    shopping: 0,
    bills: 0,
    others: 0
  })
  const httpService = new Service();
  //used to format number
  const numberFormat = (value) =>
    new Intl.NumberFormat('en-IN', {
      style: 'currency',
      currency: 'INR'
    }).format(value);


  //fetch networth from backend and expense data
  useEffect(() => {
    let obj = {};
    let netWorthData =0;
    setTimeout(() => {
      async function getDashBoardData() {
        try {
          const res = await httpService.get("getNetWorth");
          console.log("data", res)
          netWorthData = (res.balance)
          obj.netWorth = netWorthData;
        } catch (error) {
          console.log("could not fetch netWorth: exception occured: " + error);
        }
        //loop expenses data
        const promises = expenses.map(
          (data) => {
            console.log("fetching data for " + data.name)
            let value = getData(data.name)
            return value;
          }
        )
        const apiData = await Promise.all(promises);
        const jsonObject = {
          apiData: apiData
        };
        console.log("json",jsonObject);
        apiData.map((item) => {
          let name = item.name;
          switch(name) {
            case 'FOOD&RESTAURANTS':
              obj.food = item.expense
              break;
              case 'SHOPPING':
                obj.shopping = item.expense
                break;
              case 'BILLS&UTILITIES':
                obj.bills = item.expense
                break;
              case 'Adhoc&Others':
                obj.others = item.expense
                break;
              case 'GROCERIES':
                obj.groceries = item.expense
                break;  
              default:
                console.log("error, category "+name + " not found!")
            }
        })
        setdashboardData(obj)
      }
      getDashBoardData();
    }, 2000)

  }, []);

  async function getData(category) {
    try {
      const res = await httpService.get(category);
      return res;
    } catch (error) {
      console.log("could not fetch "+category+": exception occured: " + error);
    }
  }
  return (
    <div className='mt-12'>
      <div className='flex flex-wrap lg:flex-nowrap justify-center '>
        <div className='bg-white dark:text-gray-200 dark:bg-secondary-dark-bg h-44 rounded-xl w-full lg:w-80 p-8 pt-9 m-3 bg-hero-pattern bg-no-repeat bg-cover bg-center'>
          <div className='flex justify-between items-center'>
            <div>
              <p className='font-bold text-gray-400'>NET WORTH:</p>
              <p className='text-2xl'>{numberFormat(dashboardData.netWorth)}</p>
            </div>
          </div>
          <div className='mt-6'>
            < Button
              color="white"
              bgColor="blue"
              text="Download"
              borderRadius="10px"
              size="md"
            />
          </div>
        </div>
        <div className='flex m-3 flex-wrap justify-center gap-1 items-center'>
          {expenses.map(
            (item) => (
              <div key={item.title}
                className="bg-white dark:text-gray-200 dark:bg-secondary-dark-bg md:w-56 p-4 pt-9 rounded-2xl">
                <button type="button"
                  style={{ color: item.iconColor, backgroundColor: item.iconBg }}

                  className="text-2xl opacity-0.9 rounded-full p-4 hover:drop-shadow-xl">
                  {item.icon}
                </button>
                <p className='mt-3'>
                  <span className='text-lg font-semibold'>
                    {
                      numberFormat(dashboardData[item.name])
                    }
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
      <div className="flex gap-10 flex-wrap justify-center">
          <div className="bg-white dark:text-gray-200 dark:bg-secondary-dark-bg m-3 p-4 rounded-2xl md:w-780">
            <div className='flex justify-between'>
                    <p className='font-semibold text-xl'>Summary</p>
                    <div className='flex items-center gap-4'>
                      <p className='flex items-center gap-2 text-gray-600 hover:drop-shadow-xl'>
                        <span>
                          <GoPrimitiveDot/>
                        </span>
                        <span>Expense</span>
                      </p>
                      <p className='flex items-center gap-2 text-green-400 hover:drop-shadow-xl'>
                        <span>
                          <GoPrimitiveDot/>
                        </span>
                        <span>Income</span>
                      </p>
                    </div>
            </div>
            <div className='mt-10 flex gap-10 justify-center'>
                    <div className='border-r-1 border-color m-4 pr-10'>
                      <div>
                        <p>
                          <span className='text-3xl font-semibold'>{numberFormat(dashboardData.netWorth)}</span>
                          <span className='p-1.5 hover
                          drop-shadow-xl cursor-pointer rounded-full text-white bg-green-400 ml-3 text-xs'>25%</span>
                        </p>
                        <p className='text-gray-500 mt-1'>Expense</p>
                      </div>
                      <div className='mt-8'>
                        <p>
                          <span className='text-3xl font-semibold'>{numberFormat(dashboardData.netWorth-25000)}</span>
                        </p>
                        <p className='text-gray-500 mt-1'>Income</p>
                      </div>
                      <div className='mt-5'>
                      <SparkLine currentColor="blue" id="line-sparkLine" type="Line" height="80px" width="250px" data={SparklineAreaData} color="blue" />
                      </div>
                      <div className='mt-10'> 
                          <Button color="white" bgColor="blue" text ="Download Report" borderRadius="10px"/>
                      </div>
                    </div>
                    <div>
                      <Stacked width="320px" height="360px"/>
                    </div>
            </div>
          </div>
      </div>
    </div>
  )
}

export default Home