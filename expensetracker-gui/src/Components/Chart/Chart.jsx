import React, { useEffect, useState } from 'react'
import {
  Chart as ChartJs,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
  ArcElement,
} from 'chart.js'

import { Line } from 'react-chartjs-2';
import styled from 'styled-components';
import { dateFormat } from '../../utils/dateFormat';
import { useGlobalContext } from '../../context/globalContext';

ChartJs.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
  ArcElement,
)

const Chart = () => {
  const {getDashboardData, dates, incomes, expenses, investments } = useGlobalContext();
  const [isLoading,setIsLoading] = useState(true);

  useEffect(()=>{
    getDashboardData().finally(()=>setIsLoading(false));
  },[])
  const data = {
    labels: dates && dates.map((item) => {
      
      return dateFormat(item)
    }),
    datasets: [
      {
        label: 'Income',
        data: [
          ...incomes
        ],
        backgroundColor: 'green',
        tension: .2
      },
      {
        label: 'Expenses',
        data: [
          ...expenses
        ],
        backgroundColor: 'red',
        tension: .2
      },
      {
        label: 'Investments',
        data: [
          ...investments
        ],
        backgroundColor: 'blue',
        tension: .2
      }
    ]
  }
  function renderChart() {
    return (
      <ChartStyled >
        <Line data={data} />
      </ChartStyled>
    )
  }
  return (<>
    {isLoading?<p>loading chart data...</p>: renderChart()}
  </>
  )
}

const ChartStyled = styled.div`
  background: #FCF6F9;
  border: 2px solid #FFFFFF;
  box-shadow: 0px 1px 15px rgba(0, 0, 0, 0.06);
  padding: 1rem;
  border-radius: 20px;
  height: 100%;
  `;

export default Chart