export const handleFilterIDChange = (event, filterValues, setfilterValues) => {
  const { name, value } = event.currentTarget;
  setfilterValues({ ...filterValues, [name]: { ...filterValues[name], ['inputValue']: value } });
};


export const handleFilterChangeChecked = (event, filterValues, setfilterValues) => {
  const { name, checked } = event.currentTarget;
  setfilterValues({ ...filterValues, [name]: { ...filterValues[name], ['inputValue']: checked,  ['ischecked']: true } });
};

export const handleNumberSelectChanged = (event, filterValues, setfilterValues) => {
   const { name, value } = event.target;
  setfilterValues({ ...filterValues, [name]: { ...filterValues[name], ['selecType']: value } });
};

export const handleSelectValueChanged = (event, filterValues, setfilterValues) => {
  const { name, value } = event.target;
 setfilterValues({ ...filterValues, [name]: { ...filterValues[name], ['inputValue']: value } });
};

export const getFilterRequestString = (filterValues) => {
   let filterValue = '';
  for (const [key, value] of Object.entries(filterValues)) {
     /* eslint no-console: off */
   console.log(value);
 
    const filterRequestValue = value['inputValue'];
      if (typeof filterRequestValue === "boolean" && !value['ischecked']) {
      
      continue;
    }
    filterValue = filterValue + (filterRequestValue !== '' ? (value['fieldName'] + value['selecType'] + '=' + filterRequestValue + '&') : '');
  }
  
  return filterValue;
};

