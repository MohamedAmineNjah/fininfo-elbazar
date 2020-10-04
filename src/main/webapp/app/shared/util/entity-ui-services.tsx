export const validateRequired = (data, propertyToCheck, setMessageFunction) => {
  let isrequired = false;
  const messageToShow = 'Vérifiez les champs saisies';
  propertyToCheck.map(item => {
    if (data[item.field] === '' || !data[item.field]) {
     
      isrequired = true;
    }
  });
  setMessageFunction(messageToShow);
  return isrequired;
};

export const validateErrorOnField = (regexValues) => {
  let isrequired = false;
  for (const [key, value] of Object.entries(regexValues)) {
    console.log('validateErrorOnField');
   console.log(regexValues);
   if (value['isOnError']) {isrequired = true; break;} else { isrequired = false}
 }
  return isrequired;
};


export const handleInputChange = (event: any, values: any, setfunction: any) => {
  const { name, value } = event.currentTarget;

  setfunction({ ...values, [name]: value });
};

export const handleChangeChecked = (event: any, values: any, setfunction: any) => {
  const { name, checked } = event.target;
  setfunction({ ...values, [name]: checked });
};

export const handleEligibleRemiseInputChange = (event: any, values: any, setfunction: any) => {
  const { name, checked } = event.target;
  setfunction({ ...values, [name]: checked, ['remise'] : '', ['debutPromo'] : '', ['finPromo'] : '' });
};



export const onBlobChange = (isAnImage, name, setFileDataFunction, setfunction, values) => event => {
  setFileDataFunction(
    event,
    (contentType, data) => {
      setfunction({ ...values, ['imageContentType']: contentType, [name]: data });
    },
    isAnImage
  );
};

export const handelAutocomplete = (event, categoriearray, cat, values, setfunction, idname, name) => {
  const filtered = categoriearray.filter(category => category.id === cat.id);
  setfunction({ ...values, [idname]: filtered[0].id, [name]: filtered[0].nom });
};

export const handelEnableAutocomplete = (
  event,
  categoriearray,
  cat,
  values,
  setfunction,
  idname,
  name,
  disabled,
  isdisabledfunction,
  listsoucategories,
  setlistsoucategories
) => {
  const filtered = categoriearray.filter(category => category.id === cat.id);
  const filteredsoucategories = listsoucategories.filter(souscat => souscat.categorieId === cat.id);
  setfunction({ ...values, [idname]: filtered[0].id, [name]: filtered[0].nom, ['sousCategorieId']: null, ['sousCategorieNom']: ''});
  isdisabledfunction(false);
  setlistsoucategories(filteredsoucategories);
};

export const getDefaultvalue = (optionsarray: any, id: any) => {
  const filtered = optionsarray.filter(o => o.id === id);
  return filtered[0];
};

export const textRegex = { pattern: new RegExp('^[a-zA-Z ]*$'), type: 'alphabetique' };
export const numberRegex = { pattern: new RegExp('^[0-9]*$'), type: 'numerique' };
export const RefRegex = { pattern: new RegExp('^[\\S]{0,25}$'), type: 'refregex' };
export const CodeAlpha = { pattern: new RegExp('^[a-zA-Z0-9 ]*$'), type: 'alphanumerique' };
export const CodeFloat = { pattern: new RegExp('^\\d*([.0-9]{1,4})?$'), type: 'FloatRegex' };
