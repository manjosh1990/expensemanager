import React, { useEffect } from 'react'
import styled from 'styled-components'
import Chart from '../Chart/Chart';
import { InnerLayout } from '../../styles/Layout';
import { rupee } from '../../utils/icons';
import { useGlobalContext } from '../../context/globalContext';
import History from '../History/History';

const Dashboard = () => {
  const {getDashboardData, dashboardData} = useGlobalContext();
  useEffect(() => {
    const fetchDashboardData =() =>{
      getDashboardData();
    };
    fetchDashboardData();
  }, [])
  return (
    <DashboardStyled>
      <InnerLayout>
        {dashboardData ? <>
          <h1>All Transactions</h1>
        <div className="stats-con">
          <div className="chart-con">
            {dashboardData && <Chart dashboardData = {dashboardData}/>}
            <div className="amount-con">
              <div className="income">
                <h2>Total Income</h2>
                <p>
                  {rupee} {dashboardData.totalIncome?dashboardData.totalIncome : 0}
                </p>
              </div>
              <div className="expense">
                <h2>Total Expense</h2>
                <p>
                  {rupee} { dashboardData.totalExpense?dashboardData.totalExpense : 0}
                </p>
              </div>
              <div className="investment">
                <h2>Total Investment
                  <p>
                    {rupee} { dashboardData.totalInvestment?dashboardData.totalInvestment : 0}
                  </p>
                </h2>
              </div>
              <div className="balance">
                <h2>Total Balance
                  <p>
                    {rupee} { dashboardData.totalBalance?dashboardData.totalBalance : 0}
                  </p>
                </h2>
              </div>
            </div>
          </div>
          <div className="history-con">
            <History />
            <h2 className='salary-title'>Min <span>Income</span>Max</h2>
            <div className="salary-item">
              <p>
                ₹{dashboardData.minIncome?dashboardData.minIncome : 0}
              </p>
              <p>
                ₹{dashboardData.maxIncome?dashboardData.maxIncome : 0}
              </p>
            </div>
            <h2 className="salary-title">Min <span>Expense</span>Max</h2>
            <div className="salary-item">
              <p>
                ₹{dashboardData.minExpense?dashboardData.minExpense : 0}
              </p>
              <p>
                ₹{dashboardData.maxExpense?dashboardData.maxExpense : 0}
              </p>
            </div>
          </div>
        </div>
        </>:<h1>Loading...</h1>}
      </InnerLayout>
    </DashboardStyled>
  )
}

const DashboardStyled = styled.div`
  .stats-con{
    display :grid;
    grid-template-columns: repeat(5,1fr);
    gap:2rem;
    .chart-con{
      grid-column: 1/4;
      height:400px;
      .amount-con{
        display: grid;
        grid-template-columns: repeat(4,1fr);
        gap: 2rem;
        margin-top: 2rem;
        padding-bottom: 2rem;
        .income,.expense, .investment, .balance{
          grid-column: span 2;
        }
        .income, .expense, .balance,.investment{
                    background: #FCF6F9;
                    border: 2px solid #FFFFFF;
                    box-shadow: 0px 1px 15px rgba(0, 0, 0, 0.06);
                    border-radius: 20px;
                    padding: 1rem;
                    p{
                        font-size: 2.5rem;
                        font-weight: 700;
                    }
                }
      }
      .balance{
                    grid-column: 2 / 4;
                    display: flex;
                    flex-direction: column;
                    justify-content: center;
                    align-items: center;
                    p{
                        color: var(--color-green);
                        opacity: 0.6;
                        font-size: 4.5rem;
                    }
                }
    }
    .history-con{
            grid-column: 4 / -1;
            h2{
                margin: 1rem 0;
                display: flex;
                align-items: center;
                justify-content: space-between;
            }
            .salary-title{
                font-size: 1.2rem;
                span{
                    font-size: 1.8rem;
                }
            }
            .salary-item{
                background: #FCF6F9;
                border: 2px solid #FFFFFF;
                box-shadow: 0px 1px 15px rgba(0, 0, 0, 0.06);
                padding: 1rem;
                border-radius: 20px;
                display: flex;
                justify-content: space-between;
                align-items: center;
                p{
                    font-weight: 600;
                    font-size: 1.6rem;
                }
            }
        }
  }
`;


export default Dashboard