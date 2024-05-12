import React, { useEffect, useState } from 'react'
import styled from 'styled-components'
import { useGlobalContext } from '../../context/globalContext';
import DatePicker from 'react-datepicker';
import "react-datepicker/dist/react-datepicker.css";
import Button from '../Button/Button';
import { plus } from '../../utils/icons';
import { capitalizeFirstLetter } from '../../utils/stringUtils';
const Form = ({ formType }) => {
  const { addTransaction, categories, getCategories,error,setError } = useGlobalContext();
  const [inputState, setInputState] = useState({
    amount: 0,
    transactionDate: '',
    type: '',
    category: '',
    description: ''
  });
  const { amount, transactionDate, type, category, description } = inputState;
  function handleInput(name) {
    return function (e) {
      const value = e.target.value;
      if (!value.trim()) {
        setError('All fields are mandatory');
      } else {
        setError(null);
      }
      setInputState({ ...inputState, [name]:  value, type:formType})
      
    }
  }
  const isFormValid = () => {
    return Object.values(inputState).every(value => value !== '');
  };
  function handleSubmit(e) {
    e.preventDefault();
    if (isFormValid()) {
      addTransaction(inputState,type);
    }else{
      setError('All fields are mandatory');
    }
  }
  useEffect(() => {
    getCategories();
  }, []);

  const selectOptions = categories.map((category, idx) => <option key={idx} value={category}>{category}</option>)
  return (
    <FormStyled onSubmit={handleSubmit}>
      {error && <p className='error'>{error}</p>}
      <div className="input-control">
        <input
          type="text"
          value={formType}
          disabled
          name={'type'}
          placeholder={formType}
          onChange={handleInput('type')} />
      </div>
      <div className="input-control">
        <input
          type="text"
          value={amount}
          name={'amount'}
          placeholder="Amount"
          onChange={handleInput('amount')} />
      </div>
      <div className="input-control">
        <DatePicker id="date"
          placeholderText='Select transaction date'
          selected={transactionDate}
          dateFormat="dd/MM/yyyy"
          onChange={(date) => {
            setInputState({ ...inputState, transactionDate: date })
          }} />
      </div>
      <div className="selects input-control">
        <select required value={category} name="category" id="category"
          onChange={handleInput('category')}
        >
          <option value="disabled">Select Option</option>
          {selectOptions}
        </select>
      </div>
      <div className="input-control">
        <textarea
          id="description"
          value={description}
          name={'description'}
          placeholder="Description"
          cols="30"
          rows="4"
          onChange={handleInput('description')} />
      </div>
      <div className="submit-btn">
        <Button
          name={`Add ${capitalizeFirstLetter(formType)}`}
          icon={plus}
          bPad={'.8rem 1.6rem'}
          bRad={'30px'}
          bg={'var(--color-accent'}
          color={'#fff'}
        ></Button>
      </div>
    </FormStyled>
  )
}
const FormStyled = styled.form`
  display: flex;
  flex-direction: column;
  gap :2rem;
  input, textarea, select{
        font-family: inherit;
        font-size: inherit;
        outline: none;
        border: none;
        padding: .5rem 1rem;
        border-radius: 5px;
        border: 2px solid #fff;
        background: transparent;
        resize: none;
        box-shadow: 0px 1px 15px rgba(0, 0, 0, 0.06);
        color: rgba(34, 34, 96, 0.9);
        &::placeholder{
            color: rgba(34, 34, 96, 0.4);
        }
    }
    .input-control{
        input{
            width: 100%;
        }
    }

    .selects{
        display: flex;
        justify-content: flex-end;
        select{
            color: rgba(34, 34, 96, 0.4);
            &:focus, &:active{
                color: rgba(34, 34, 96, 1);
            }
        }
    }
    .submit-btn{
        button{
            box-shadow: 0px 1px 15px rgba(0, 0, 0, 0.06);
            &:hover{
                background: var(--color-green) !important;
            }
        }
    }
`;
export default Form;