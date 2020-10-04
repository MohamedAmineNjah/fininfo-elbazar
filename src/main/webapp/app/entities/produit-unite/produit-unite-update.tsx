import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Row, Col } from 'reactstrap';
import { IRootState } from 'app/shared/reducers';
import { getEntity, updateEntity, createEntity, reset } from './produit-unite.reducer';
import { IProduitUnite } from 'app/shared/model/produit-unite.model';
import ArrowBackIcon from '@material-ui/icons/ArrowBack';
import SaveIcon from '@material-ui/icons/Save';
import Button from '@material-ui/core/Button';
import Card from '@material-ui/core/Card';
import CardHeader from '@material-ui/core/CardHeader';
import {
  Typography,
  ButtonGroup,
} from '@material-ui/core';

import { TextValidator, SnackBarCustom } from '../produit/product-validation';
import { CodeAlpha, validateRequired, validateErrorOnField } from 'app/shared/util/entity-ui-services';
import { useStyles } from 'app/shared/util/entity-style';

export interface IProduitUniteUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> { }

export const ProduitUniteUpdate = (props: IProduitUniteUpdateProps) => {

  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);
  const [valeurs, setvaleurs] = useState<IProduitUnite>({});
  const [errorOnField, seterrorOnField] = useState(false);
  const [openSnackMessage, setopenSnackMessage] = useState('');
  const [openSnack, setopenSnack] = useState(false);
  const [regexValues, setregexValues] = useState({ 
    nom:  {isOnError : false },
    code:  {isOnError : false },
  });


  const handleCloseSnack = () => {
    setopenSnack(false);
  };

  const requiredValues = [
    { field: 'nom', fieldName: 'Nom ' },
    { field: 'code', fieldName: 'code' },
  ];

  const classes = useStyles();

  const handleClose = () => {
    props.history.push('/produit-unite');
  };


  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (!props.loading) {
      if (isNew) { setvaleurs({}); }
      else {
        setvaleurs(props.produitUniteEntity);
      }
    }
  }, [props.loading]);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);


  const saveEntity = () => {
    if (!validateRequired(valeurs, requiredValues, setopenSnackMessage) &&  !validateErrorOnField(regexValues)) {
      if (isNew) {
        valeurs.id = null;
        props.createEntity(valeurs);
      } else {
        props.updateEntity(valeurs);
      }
    } else {
      setopenSnack(true);
    }
  };

  const clearValeurs = () => {
    setvaleurs({});
    handleClose();
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
                    Fiche unité produit {valeurs.nom ? valeurs.nom : ''}
                  </Typography>
                ) : (
                    <Typography component="h4" gutterBottom variant="body2" color="textSecondary" style={{ fontSize: '1.875rem' }}>
                      Fiche nouvelle unité produit
                    </Typography>
                  )}
              </Col>
              <Col md="3">
                <ButtonGroup>

                  <Button id="cancel-save" onClick={clearValeurs} variant="contained" color="primary" className={classes.button} startIcon={<ArrowBackIcon />}>
                    Retour
                  </Button>

                  <Button
                    id="save-entity"
                    type="submit"
                    onClick={saveEntity}
                    variant="contained"
                    className={classes.button}
                    color="primary"
                    startIcon={<SaveIcon />}
                  >
                    Enregistrer
                </Button>
                </ButtonGroup>
              </Col>
            </Row>
            <Card className={(classes.root2, 'container')} style={{ height: '85%', width: '40%' }}>
              <CardHeader title="Informations unité produit" />

              <form className={classes.root}>
                <Row className="justify-content-center">
                  <Col>
                    <Row>
                      <TextValidator
                        id="produit-unite-code"
                        label="Code de l'unité produit"
                        name="code"
                        values={{ data: valeurs, field: 'code', setter: setvaleurs, errorsetter: seterrorOnField, regValue: regexValues,  globalErrorSetter: setregexValues }}
                        required
                        regx={CodeAlpha}
                        style={{ marginBottom: '20px', width: 250 }}
                      />
                    </Row>
                    <br />
                    <Row>
                      <TextValidator
                        id="produit-unite-nom"
                        label="Nom de l'unité produit"
                        name="nom"
                        values={{ data: valeurs, field: 'nom', setter: setvaleurs, errorsetter: seterrorOnField, regValue: regexValues,  globalErrorSetter: setregexValues }}
                        required
                        regx={CodeAlpha}
                        style={{ marginBottom: '20px', width: 250 }}
                      />
                    </Row>
                  </Col>
                </Row>
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
  produitUniteEntity: storeState.produitUnite.entity,
  loading: storeState.produitUnite.loading,
  updating: storeState.produitUnite.updating,
  updateSuccess: storeState.produitUnite.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProduitUniteUpdate);
