import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, openFile, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import ArrowBackIcon from '@material-ui/icons/ArrowBack';
import SaveIcon from '@material-ui/icons/Save';
import {
  handleChangeChecked,
  onBlobChange,
  validateRequired,
  validateErrorOnField,
  handleInputChange,
} from 'app/shared/util/entity-ui-services';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './slides.reducer';
import { ISlides } from 'app/shared/model/slides.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import {
  Card,
  CardHeader,
  FormGroup,
  FormControlLabel,
  Switch,
  Typography,
  makeStyles,
  createStyles,
  Theme,
  TableContainer,
  Table,
  TableHead,
  TableRow,
  TableCell,
  Paper,
  TableBody,
  withStyles,
  ButtonGroup,
  Button,
  FormControl,
  TextField,
} from '@material-ui/core';
import { useStylesClient, useStyles } from 'app/shared/util/entity-style';

export interface ISlidesUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SlidesUpdate = (props: ISlidesUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);
  const [valeurs, setvaleurs] = useState<ISlides>({});
  const [openSnack, setopenSnack] = useState(false);
  const [openSnackMessage, setopenSnackMessage] = useState('');
  const [regexValues, setregexValues] = useState({
    position: { isOnError: false },
  });
  const requiredValues = [{ field: 'image', fieldName: 'image ' }];
  const { slidesEntity, loading, updating } = props;

  const { image, imageContentType } = slidesEntity;

  const handleClose = () => {
    props.history.push('/slides');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (!props.loading) {
      if (isNew) {
        setvaleurs({});
      } else {
        setvaleurs(props.slidesEntity);
      }
    }
  }, [props.loading]);

  const clearBlob = name => () => {
    props.setBlob(name, undefined, undefined);
  };

  useEffect(() => {
    if (props.updateSuccess) {
      setvaleurs({});
      handleClose();
    }
  }, [props.updateSuccess]);
  const classes = useStyles();

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
              <Typography component="h4" gutterBottom variant="body2" color="textSecondary" style={{ fontSize: '1.875rem' }}>
                Fiche de {valeurs ? valeurs.nom : ''}
              </Typography>
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
                  color="primary"
                  onClick={saveEntity}
                  className={classes.button}
                  startIcon={<SaveIcon />}
                >
                  Enregistrer
                </Button>
              </ButtonGroup>
            </Col>
          </Row>
          <Row>
            <Card className={(classes.root, 'container')} style={{ height: '70%', width: '50%' }}>
              <CardHeader title="Mise à jour Slide" subheader="Informations concernant les slides" />
              <Row>
                <Col md="6">
                  <div className={classes.root}>
                    {' '}
                    {'Nom : '}
                    {valeurs.nom}
                  </div>
                </Col>
                <Col md="6">
                  <div className={classes.root}>
                    {'Type du slide : '}
                    {valeurs.type}
                  </div>
                </Col>
              </Row>
              <br />
              <Row>
                <Col md="6">
                  <TextField
                    id="lien-id"
                    label="Lien du Slide"
                    name="lien"
                    value={valeurs ? valeurs.lien : ''}
                    onChange={e => handleInputChange(e, valeurs, setvaleurs)}
                    style={{ width: 250 }}
                  />
                </Col>

                <Col md="6">
                  <FormControl>
                    <div>
                      <Row className="justify-content-center">
                        {valeurs && valeurs.image ? (
                          <div>
                            <a onClick={openFile(valeurs.imageContentType, valeurs.image)}>
                              <img
                                src={`data:${valeurs.imageContentType};base64,${valeurs.image}`}
                                style={{ maxHeight: '70px' }}
                                className="justify-content-center"
                              />
                            </a>
                          </div>
                        ) : null}
                      </Row>
                      <Row>
                        <input
                          accept="image/*"
                          className={classes.input}
                          id="file_image"
                          type="file"
                          onChange={onBlobChange(true, 'image', setFileData, setvaleurs, valeurs)}
                        />
                        <label htmlFor="file_image">
                          <Button variant="outlined" color="primary" component="span" style={{ height: '40px' }}>
                            {valeurs ? (!valeurs.image ? 'Ajouter Image *' : 'Modifier Image') : ''}
                          </Button>
                        </label>
                        <input type="hidden" name="image" value={valeurs ? valeurs.image : ''} required />
                      </Row>

                      <br />
                    </div>
                  </FormControl>
                </Col>
              </Row>
            </Card>
          </Row>
        </div>
      )}
    </div>
  );
};
const mapStateToProps = (storeState: IRootState) => ({
  slidesEntity: storeState.slides.entity,
  loading: storeState.slides.loading,
  updating: storeState.slides.updating,
  updateSuccess: storeState.slides.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SlidesUpdate);
