import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './slides.reducer';
import { ISlides } from 'app/shared/model/slides.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISlidesDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SlidesDetail = (props: ISlidesDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { slidesEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="elbazarApp.slides.detail.title">Slides</Translate> [<b>{slidesEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="nom">
              <Translate contentKey="elbazarApp.slides.nom">Nom</Translate>
            </span>
          </dt>
          <dd>{slidesEntity.nom}</dd>
          <dt>
            <span id="image">
              <Translate contentKey="elbazarApp.slides.image">Image</Translate>
            </span>
          </dt>
          <dd>
            {slidesEntity.image ? (
              <div>
                {slidesEntity.imageContentType ? (
                  <a onClick={openFile(slidesEntity.imageContentType, slidesEntity.image)}>
                    <img src={`data:${slidesEntity.imageContentType};base64,${slidesEntity.image}`} style={{ maxHeight: '30px' }} />
                  </a>
                ) : null}
                <span>
                  {slidesEntity.imageContentType}, {byteSize(slidesEntity.image)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="type">
              <Translate contentKey="elbazarApp.slides.type">Type</Translate>
            </span>
          </dt>
          <dd>{slidesEntity.type}</dd>
        </dl>
        <Button tag={Link} to="/slides" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/slides/${slidesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ slides }: IRootState) => ({
  slidesEntity: slides.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SlidesDetail);
