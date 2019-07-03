

import React, { Component } from 'react'
import FontAwesome from 'react-fontawesome';
import { connect } from 'dva'
import moment from 'moment'
import BooleanOption from 'components/BooleanOption';
import { Row, Col, Icon, Card, Tabs, Table, Radio, DatePicker, Tooltip, Menu, Dropdown,Badge, Switch,Select,Form,AutoComplete,Modal } from 'antd'
import { Link, Route, Redirect} from 'dva/router'
import numeral from 'numeral'

import DashboardTool from '../../common/Dashboard.tool'
import PageHeaderLayout from '../../layouts/PageHeaderLayout'
import styles from './ShippingSpace.profile.less'
import DescriptionList from '../../components/DescriptionList';

import GlobalComponents from '../../custcomponents';
import PermissionSetting from '../../permission/PermissionSetting'
import appLocaleName from '../../common/Locale.tool'
const { Description } = DescriptionList;
const {defaultRenderExtraHeader}= DashboardTool


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////


const internalSummaryOf = (shippingSpace,targetComponent) =>{
    const userContext = null
	return (
	<DescriptionList className={styles.headerList} size="small" col="4">
<Description term="序号">{shippingSpace.id}</Description> 
<Description term="位置">{shippingSpace.location}</Description> 
<Description term="联系电话">{shippingSpace.contactNumber}</Description> 
<Description term="总面积">{shippingSpace.totalArea}</Description> 
<Description term="纬度">{shippingSpace.latitude}</Description> 
<Description term="经度">{shippingSpace.longitude}</Description> 
<Description term="描述">{shippingSpace.description}</Description> 
<Description term="最后更新时间">{ moment(shippingSpace.lastUpdateTime).format('YYYY-MM-DD')}</Description> 
	
      </DescriptionList>
	)
}


const renderPermissionSetting = shippingSpace => {
  const {ShippingSpaceBase} = GlobalComponents
  return <PermissionSetting targetObject={shippingSpace}  targetObjectMeta={ShippingSpaceBase}/>
}

const internalRenderExtraHeader = defaultRenderExtraHeader

class ShippingSpacePermission extends Component {


  componentDidMount() {

  }
  

  render() {
    // eslint-disable-next-line max-len
    const  shippingSpace = this.props.shippingSpace;
    const { id,displayName, goodsCount } = shippingSpace
    const cardsData = {cardsName:"发货区",cardsFor: "shippingSpace",cardsSource: shippingSpace,
  		subItems: [
    
      	],
  	};
    const renderExtraHeader = this.props.renderExtraHeader || internalRenderExtraHeader
    const summaryOf = this.props.summaryOf || internalSummaryOf
   
    return (

      <PageHeaderLayout
        title={`${cardsData.cardsName}: ${displayName}`}
        content={summaryOf(cardsData.cardsSource,this)}
        wrapperClassName={styles.advancedForm}
      >
      {renderExtraHeader(cardsData.cardsSource)}
      {renderPermissionSetting(cardsData.cardsSource)}
      
      </PageHeaderLayout>
    )
  }
}

export default connect(state => ({
  shippingSpace: state._shippingSpace,
}))(Form.create()(ShippingSpacePermission))
