import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Row, Col } from 'reactstrap';
import { IRootState } from 'app/shared/reducers';

import { getAllEntities as getProduits } from 'app/entities/produit/produit.reducer';

import { getEntities as getCommandes } from 'app/entities/commande/commande.reducer';
import { getEntity, updateEntity, createEntity, reset } from './mouvement-stock.reducer';

import { makeStyles } from '@material-ui/core/styles';
import ArrowBackIcon from '@material-ui/icons/ArrowBack';
import SaveIcon from '@material-ui/icons/Save';
import lightGreen from '@material-ui/core/colors/lightGreen';
import Button from '@material-ui/core/Button';
import Card from '@material-ui/core/Card';
import CardHeader from '@material-ui/core/CardHeader';
import blue from '@material-ui/core/colors/blue';
import {
  Typography,
  TextField,
  FormControl,
  NativeSelect,
  InputLabel,
  FormControlLabel,
  Checkbox,
  FormGroup,
  ButtonGroup,
} from '@material-ui/core';

import { IMouvementStock } from 'app/shared/model/mouvement-stock.model';
import { TypeMvt } from 'app/shared/model/enumerations/type-mvt.model';
import { useStyles } from 'app/shared/util/entity-style';
import { handleInputChange, numberRegex, CodeAlpha, validateErrorOnField } from 'app/shared/util/entity-ui-services';
import { hasAnyAuthority } from 'app/shared/auth/private-route';
import { AUTHORITIES } from 'app/config/constants';
import Autocomplete from '@material-ui/lab/Autocomplete';
import { IProduit } from 'app/shared/model/produit.model';
import matchSorter from 'match-sorter';
import { TextValidator, SnackBarCustom } from '../produit/product-validation';

export interface IMouvementStockUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MouvementStockUpdate = (props: IMouvementStockUpdateProps) => {
  const { isAdmin, mouvementStockEntity, produits, commandes, loading, updating } = props;

  const [listProduits, setlistProduits] = useState<IProduit[]>([]);
  const [refProduitId, setRefProduitId] = useState('0');
  const [refCommandeId, setRefCommandeId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);
  const [valeurs, setvaleurs] = useState<IMouvementStock>({});
  const [openSnack, setopenSnack] = useState(false);
  const [openSnackMessage, setopenSnackMessage] = useState('');
  const [errorOnField, seterrorOnField] = useState(false);
  const [regexValues, setregexValues] = useState({
    quantite: { isOnError: false },
  });

  const requiredValues = [
    { field: 'quantite', fieldName: 'Quantite ' },
    { field: 'refProduitId', fieldName: 'Description' },
    { field: 'refProduitReference', fieldName: 'Position' },
  ];

  const classes = useStyles();

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getProduits();
  }, []);

  const handleClose = () => {
    props.history.push('/mouvement-stock' + props.location.search);
  };

  const handleCloseSnack = () => {
    setopenSnack(false);
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  useEffect(() => {
    if (props.produits) {
      setlistProduits([...props.produits]);
    }
  }, [props.produits]);

  useEffect(() => {
    if (!props.loading) {
      if (isNew) {
        setvaleurs({});
      } else {
        setvaleurs(props.mouvementStockEntity);
      }
    }
  }, [props.loading]);

  const validateRequired = (data, propertyToCheck, setMessageFunction) => {
    let isrequired = false;
    const messageToShow = 'Des champs obligatoires ne sont pas saisies';
    propertyToCheck.map(item => {
      if (data[item.field] === '' || !data[item.field]) {
        isrequired = true;
      }
    });
    setMessageFunction(messageToShow);
    return isrequired;
  };

  const saveEntity = () => {
    if (!validateRequired(valeurs, requiredValues, setopenSnackMessage) && !validateErrorOnField(regexValues)) {
      if (!valeurs['type']) {
        valeurs['type'] = TypeMvt.EntreeStock;
      }
      props.createEntity(valeurs);
    } else {
      setopenSnack(true);
    }
  };

  const clearValeurs = () => {
    setvaleurs({});
    handleClose();
  };

  const isAdminMvntType = isAdministrator => {
    if (isAdministrator) {
      return (
        <React.Fragment>
          <option value="EntreeStock">Entrée en stock</option>
          <option value="SortieStock">Sortie stock correction</option>
        </React.Fragment>
      );
    } else {
      return (
        <React.Fragment>
          <option value="EntreeStock">Entrée en stock</option>
        </React.Fragment>
      );
    }
  };

  const calculateCurrentdate = () => {
    const today = new Date();
    const dd = String(today.getDate()).padStart(2, '0');
    const mm = String(today.getMonth() + 1).padStart(2, '0');
    const yyyy = today.getFullYear();
    const todayDate = yyyy + '-' + mm + '-' + dd;
    valeurs.date = todayDate;
    return todayDate;
  };

  const filterOptions = (options, { inputValue }): IProduit[] => {
    if (inputValue === '') {
      return options;
    } else {
      return matchSorter(options, inputValue, { keys: ['reference', 'nom'] });
    }
  };

  const handleProduitChange = (event, produit: any) => {
    const { name } = event.currentTarget;
    setvaleurs({ ...valeurs, ['refProduitId']: produit.id, ['refProduitReference']: produit.reference });
  };

  return (
    <div>
      {props.loading ? (
        <p>Loading...</p>
      ) : (
        <div>
          <Row>
            <Col md="9">
              {!isNew ? (
                <Typography component="h4" gutterBottom variant="body2" color="textSecondary" style={{ fontSize: '1.875rem' }}>
                  Fiche du mouvement stock {valeurs.id ? valeurs.id : ''}
                </Typography>
              ) : (
                <Typography component="h4" gutterBottom variant="body2" color="textSecondary" style={{ fontSize: '1.875rem' }}>
                  Ajouter un mouvement stock
                </Typography>
              )}
            </Col>
            <Col md="3">
              <ButtonGroup>
                <Button
                  id="cancel-save"
                  onClick={clearValeurs}
                  variant="contained"
                  color="primary"
                  className={classes.button}
                  startIcon={<ArrowBackIcon />}
                >
                  Retour
                </Button>
                <Button
                  id="save-entity"
                  type="submit"
                  variant="contained"
                  className={classes.button}
                  onClick={saveEntity}
                  color="primary"
                  startIcon={<SaveIcon />}
                >
                  Enregistrer
                </Button>
              </ButtonGroup>
            </Col>
          </Row>
          <Card className={(classes.root2, 'container')} style={{ height: '85%', width: '40%' }}>
            <CardHeader title="Détails mouvement stock" />

            <form className={classes.root}>
              <Col>
                {!isNew ? (
                  <Row>
                    <TextField
                      id="mouvement-stock-id"
                      name="id"
                      onChange={e => handleInputChange(e, valeurs, setvaleurs)}
                      value={valeurs.id ? valeurs.id : ''}
                      disabled
                      variant="filled"
                    />
                  </Row>
                ) : null}
                <Row>
                  <Col md="6">
                    <br />
                    <TextField
                      style={{ width: '200px' }}
                      id="mouvement-stock-date"
                      label="date du mouvement "
                      type="date"
                      name="date"
                      onChange={e => handleInputChange(e, valeurs, setvaleurs)}
                      value={calculateCurrentdate()}
                      InputLabelProps={{
                        shrink: true,
                      }}
                      disabled
                    />
                  </Col>
                  <Col md="6">
                    <FormControl className={classes.formControl} style={{ marginTop: '20px' }}>
                      <InputLabel shrink htmlFor="type-mouvement">
                        Type de mouvement
                      </InputLabel>
                      <NativeSelect
                        id="type-mouvement"
                        className={classes.selectEmpty}
                        defaultValue="EntreeStock"
                        value={valeurs ? valeurs.type : ''}
                        onChange={e => handleInputChange(e, valeurs, setvaleurs)}
                        name="type"
                        disabled={!isAdmin}
                        style={{ width: '200px' }}
                      >
                        {isAdminMvntType(isAdmin)}
                      </NativeSelect>
                    </FormControl>
                  </Col>
                </Row>

                <Row>
                  <Col md="6">
                    <TextValidator
                      id="mouvement-stock-quantite"
                      label="Quantité du mouvement"
                      name="quantite"
                      values={{
                        data: valeurs,
                        field: 'quantite',
                        setter: setvaleurs,
                        errorsetter: seterrorOnField,
                        regValue: regexValues,
                        globalErrorSetter: setregexValues,
                      }}
                      required
                      regx={numberRegex}
                      style={{ marginBottom: '20px', width: '200px' }}
                    />
                  </Col>
                  <Col md="6">
                    <TextField
                      id="reference"
                      label="Origine du mouvement"
                      name="reference"
                      onChange={e => handleInputChange(e, valeurs, setvaleurs)}
                      value={valeurs ? valeurs.reference : ''}
                      style={{ marginBottom: '20px', width: '203px' }}
                    />
                  </Col>
                </Row>

                <Row>
                  <FormControl className={classes.formControl}>
                    <Autocomplete
                      id="ref-produit"
                      style={{ width: '450px' }}
                      options={listProduits ? listProduits : []}
                      getOptionLabel={option => option.nom}
                      renderOption={option => (
                        <React.Fragment>
                          <span>
                            {'['}
                            {option.reference}
                            {']'}
                            {'  '}
                          </span>
                          {option.nom}
                        </React.Fragment>
                      )}
                      onChange={(e, value) => handleProduitChange(e, value)}
                      filterOptions={(options, state) => filterOptions(options, state)}
                      renderInput={params => <TextField {...params} name="ref-produit" label="Référence produit *" />}
                    />
                  </FormControl>
                </Row>
                <br />
              </Col>
            </form>
          </Card>
        </div>
      )}
      <i style={{ color: 'grey' }}>* Champs obligatoires</i>
      <SnackBarCustom textMessage={openSnackMessage} isSnackOpen={openSnack} handleClose={handleCloseSnack} />
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  produits: storeState.produit.entities,
  commandes: storeState.commande.entities,
  mouvementStockEntity: storeState.mouvementStock.entity,
  loading: storeState.mouvementStock.loading,
  updating: storeState.mouvementStock.updating,
  updateSuccess: storeState.mouvementStock.updateSuccess,
  isAdmin: hasAnyAuthority(storeState.authentication.account.authorities, [AUTHORITIES.ADMIN]),
});

const mapDispatchToProps = {
  getProduits,
  getCommandes,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MouvementStockUpdate);
