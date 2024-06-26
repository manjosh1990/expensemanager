import { transactions,trend,expenses,dashboard, investement } from "./icons"

export const menuItems = [
  {
    id: 1,
    title: 'Dashboard',
    icon: dashboard,
    link: '/dashboard',
    formType:""
},

{
    id: 3,
    title: "Income",
    icon: trend,
    link: "/dashboard",
    formType:"INCOME"
},
{
    id: 4,
    title: "Expenses",
    icon: expenses,
    link: "/dashboard",
    formType:"EXPENSE"
},
{
    id: 5,
    title: "Investments",
    icon: investement,
    link: "/dashboard",
    formType:"INVESTMENT"
},
{
    id: 2,
    title: "View Transactions",
    icon: transactions,
    link: "/dashboard",
    formType:""
},
]