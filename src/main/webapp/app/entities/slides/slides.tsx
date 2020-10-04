import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { openFile, byteSize, Translate, translate, ICrudSearchAction, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities } from './slides.reducer';
import { ISlides } from 'app/shared/model/slides.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISlidesProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Slides = (props: ISlidesProps) => {
  const [search, setSearch] = useState('');

  useEffect(() => {
    props.getEntities();
  }, []);

  const startSearching = () => {
    if (search) {
      props.getSearchEntities(search);
    }
  };

  const clear = () => {
    setSearch('');
    props.getEntities();
  };

  const handleSearch = event => setSearch(event.target.value);

  const { slidesList, match, loading } = props;
  return (
    <div>
      <Row className="justify-content-center">
        <Col md="6">
          <h2 id="slides-heading">
            <Translate contentKey="elbazarApp.slides.home.title">Slides</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="6">
          <AvForm onSubmit={startSearching}>
            <AvGroup>
              <InputGroup>
                <AvInput
                  type="text"
                  name="search"
                  value={search}
                  onChange={handleSearch}
                  placeholder={translate('elbazarApp.slides.home.search')}
                />
                <Button className="input-group-addon">
                  <FontAwesomeIcon icon="search" />
                </Button>
                <Button type="reset" className="input-group-addon" onClick={clear}>
                  <FontAwesomeIcon icon="trash" />
                </Button>
              </InputGroup>
            </AvGroup>
          </AvForm>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="6">
          <div className="table-responsive">
            {slidesList && slidesList.length > 0 ? (
              <Table responsive hover size="sm">
                <thead>
                  <tr>
                    <th>
                      <Translate contentKey="elbazarApp.slides.nom">Nom</Translate>
                    </th>
                    <th>
                      <Translate contentKey="elbazarApp.slides.type">Type</Translate>
                    </th>
                    <th>
                      <Translate contentKey="elbazarApp.slides.lien">lien</Translate>
                    </th>
                    <th>
                      <Translate contentKey="elbazarApp.slides.image">Image</Translate>
                    </th>

                    <th />
                  </tr>
                </thead>
                <tbody>
                  {slidesList.map((slides, i) => (
                    <tr key={`entity-${i}`}>
                      <td>{slides.nom}</td>
                      <td>{slides.type}</td>
                      <td>{slides.lien}</td>
                      <td>
                        {slides.image ? (
                          <div>
                            {slides.imageContentType ? (
                              <a onClick={openFile(slides.imageContentType, slides.image)}>
                                <img src={`data:${slides.imageContentType};base64,${slides.image}`} style={{ maxHeight: '30px' }} />
                                &nbsp;
                              </a>
                            ) : null}
                            <span>
                              {slides.imageContentType}, {byteSize(slides.image)}
                            </span>
                          </div>
                        ) : null}
                      </td>

                      <td className="text-right">
                        <div className="btn-group flex-btn-group-container">
                          <Button tag={Link} to={`${match.url}/${slides.id}/edit`} color="primary" size="sm">
                            <FontAwesomeIcon icon="pencil-alt" />{' '}
                            <span className="d-none d-md-inline">
                              <Translate contentKey="entity.action.edit">Edit</Translate>
                            </span>
                          </Button>
                        </div>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </Table>
            ) : (
              !loading && (
                <div className="alert alert-warning">
                  <Translate contentKey="elbazarApp.slides.home.notFound">No Slides found</Translate>
                </div>
              )
            )}
          </div>
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = ({ slides }: IRootState) => ({
  slidesList: slides.entities,
  loading: slides.loading,
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Slides);
