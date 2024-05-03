import { transactions,trend,expenses,dashboard, investement } from "./icons"

export const menuItems = [
  {
    id: 1,
    title: 'Dashboard',
    icon: dashboard,
    link: '/dashboard'
},
{
    id: 2,
    title: "View Transactions",
    icon: transactions,
    link: "/dashboard",
},
{
    id: 3,
    title: "Income",
    icon: trend,
    link: "/dashboard",
},
{
    id: 4,
    title: "Expenses",
    icon: expenses,
    link: "/dashboard",
},
{
    id: 5,
    title: "Investments",
    icon: investement,
    link: "/dashboard"
}
]