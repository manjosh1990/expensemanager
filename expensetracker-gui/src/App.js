import React, { useMemo, useState } from "react";
import styled from "styled-components";
import { MainLayout } from "./styles/Layout";
import Orb from "./Components/Orb/Orb";
import Navigation from "./Components/Navigation/Navigation";
import Dashboard from "./Components/Dashboard/Dashboard";
import Transactions from "./Components/Transactions/Transactions";
import AllTransactions from "./Components/AllTransactions/AllTransactions";
function App() {
  const [active, setActive] = useState(1);
  const[formType,setFormType] = useState("");
  const orbMemo = useMemo(() => {
    return <Orb />;
  }, []);

  const displayData = () => {
  
    switch (active) {
      case 1:
        return <Dashboard />;
      case 2:
        return <AllTransactions />;
      case 3:
        return <Transactions type={formType}  />;
      case 4:
        return <Transactions type={formType} />;
      case 5:
        return <Transactions type={formType} />;
      default:
        return <Dashboard />;
    }
  };
  return (
    <AppStyled>
      {orbMemo}
      <MainLayout>
        <Navigation active={active} setActive={setActive} setFormType={setFormType}/>
        <main>{displayData()}</main>
      </MainLayout>
    </AppStyled>
  );
}

const AppStyled = styled.div`
  height: 100vh;
  background-color: #d3c5e5;
  main {
    flex: 1;
    background: rgba(252, 246, 249, 0.78);
    border: 3px solid #ffffff;
    backdrop-filter: blur(4.5px);
    border-radius: 32px;
    overflow: auto;
    overflow-x: hidden;
    &::-webkit-scrollbar {
      width: 0;
    }
  }
`;
export default App;
