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

const Chart = ({dashboardData}) => {
  const [items, setItems] = useState([]);
  const [dates, setDates] = useState([]);
  const [incomes, setIncomes] = useState([]);
  const [expenses, setExpenses] = useState([]);
  const [investments, setInvestments] = useState([]);
  useEffect(() => {
    if (dashboardData && dashboardData.chartData) {
      setItems(dashboardData.chartData);
      setIncomes(items && items.filter((item) => item.type === "INCOME"))
      setExpenses(items && items.filter((item) => item.type === "EXPENSE"))
      setInvestments(items &&items.filter((item) => item.type === "INVESTMENT"))
      setDates(getUniqueDates);
    }
  }, [dashboardData])
  console.log(items)
  const getUniqueDates = () => {
    const seenDates = new Set();
    const uniqueDates = [];
    items.forEach((item) => {
      if (!seenDates.has(item.transactionDate)) {
        seenDates.add(item.transactionDate);
        uniqueDates.push(item);
      }
    });
    return uniqueDates;
  };
  const data = {
    labels: dates && dates.map((item) => {
      const { transactionDate } = item;
      return dateFormat(transactionDate)
    }),
    datasets: [
      {
        label: 'Income',
        data: [
          ...incomes.map((income) => {
            const { amount } = income
            return amount
          })
        ],
        backgroundColor: 'green',
        tension: .2
      },
      {
        label: 'Expenses',
        data: [
          ...expenses.map((expense) => {
            const { amount } = expense
            return amount
          })
        ],
        backgroundColor: 'red',
        tension: .2
      },
      {
        label: 'Investments',
        data: [
          ...investments.map((investment) => {
            const { amount } = investment
            return amount
          })
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
    {items && dates && renderChart()}
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