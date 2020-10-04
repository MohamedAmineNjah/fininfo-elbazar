import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Row, Col } from 'reactstrap';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './zone.reducer';
import ArrowBackIcon from '@material-ui/icons/ArrowBack';
import SaveIcon from '@material-ui/icons/Save';
import Button from '@material-ui/core/Button';
import Card from '@material-ui/core/Card';
import CardHeader from '@material-ui/core/CardHeader';
import {
  Typography,
  ButtonGroup,
} from '@material-ui/core';
import { IZone } from 'app/shared/model/zone.model';
import { validateRequired, CodeAlpha, validateErrorOnField } from 'app/shared/util/entity-ui-services';
import { SnackBarCustom, TextValidator } from '../produit/product-validation';
import { useStyles } from 'app/shared/util/entity-style';

export interface IZoneUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> { }

export const ZoneUpdate = (props: IZoneUpdateProps) => {

  const [isNew, setisNew] = useState(!props.match.params || !props.match.params.id);
  const [valeurs, setvaleurs] = useState<IZone>({});
  const [errorOnField, seterrorOnField] = useState(false);
  const [openSnack, setopenSnack] = useState(false);
  const [openSnackMessage, setopenSnackMessage] = useState('');
  const [regexValues, setregexValues] = useState({ 
    nom:  {isOnError : false },
    codeZone:  {isOnError : false },
  });


  const requiredValues = [
    { field: 'codeZone', fieldName: 'Code de la zone' },
    { field: 'nom', fieldName: 'Nom de la zone' },
  ];


  const handleClose = () => {
    props.history.push('/zone');
  };
  const handleCloseSnack = () => {
    setopenSnack(false);
  };

  const classes = useStyles();


  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  useEffect(() => {
    if (!props.loading) {
      if (isNew) { setvaleurs({}); }
      else {
        setvaleurs(props.zoneEntity);
      }
    }
  }, [props.loading]);

  const saveEntity = () => {
    if (!validateRequired(valeurs, requiredValues, setopenSnackMessage) && !validateErrorOnField(regexValues)) {
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
                    Fiche de la zone {valeurs.nom ? valeurs.nom : ''}
                  </Typography>
                ) : (
                    <Typography component="h4" gutterBottom variant="body2" color="textSecondary" style={{ fontSize: '1.875rem' }}>
                      Fiche de la nouvelle zone
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
                    variant="contained"
                    color="primary"
                    onClick={saveEntity}
                    className={classes.button}
                    startIcon={<SaveIcon />}
                  >
                    Enregistrer
                </Button>{' '}
                </ButtonGroup>
              </Col>
            </Row>

            <Card className={(classes.root2, 'container')} style={{ height: '85%', width: '40%' }}>
              <CardHeader title="Détails zones" />

              <form className={classes.root}>
                <br />
                <Row>
                  <TextValidator
                    id="zone-codeZone"
                    label="Code de la zone"
                    name="codeZone"
                    values={{ data: valeurs, field: 'codeZone', setter: setvaleurs, errorsetter: seterrorOnField, regValue: regexValues,  globalErrorSetter: setregexValues }}
                    regx={CodeAlpha}
                    required
                  />
                </Row>
                <br />
                <Row>
                  <TextValidator
                    id="zone-nom"
                    label="Nom de la zone"
                    name="nom"
                    values={{ data: valeurs, field: 'nom', setter: setvaleurs, errorsetter: seterrorOnField, regValue: regexValues,  globalErrorSetter: setregexValues }}
                    regx={CodeAlpha}
                    required
                  />
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
  zoneEntity: storeState.zone.entity,
  loading: storeState.zone.loading,
  updating: storeState.zone.updating,
  updateSuccess: storeState.zone.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ZoneUpdate);
