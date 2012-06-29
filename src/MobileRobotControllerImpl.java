// -*- Java -*-
/*!
 * @file  MobileRobotControllerImpl.java
 * @brief Controller
 * @date  $Date$
 *
 * $Id$
 */

import jp.go.aist.rtm.RTC.DataFlowComponentBase;
import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.port.InPort;
import jp.go.aist.rtm.RTC.port.OutPort;
import jp.go.aist.rtm.RTC.util.DataRef;
import RTC.ReturnCode_t;
import RTC.Time;
import RTC.TimedBooleanSeq;
import RTC.TimedDoubleSeq;
import RTC.TimedPose2D;
import RTC.TimedVelocity2D;
import RTC.Velocity2D;

/*!
 * @class MobileRobotControllerImpl
 * @brief Controller
 *
 */
public class MobileRobotControllerImpl extends DataFlowComponentBase {

	ControllerFrame frame;
  /*!
   * @brief constructor
   * @param manager Maneger Object
   */
	public MobileRobotControllerImpl(Manager manager) {  
        super(manager);
        // <rtc-template block="initializer">
        m_pos_val = new TimedPose2D();
        m_pos = new DataRef<TimedPose2D>(m_pos_val);
        m_posIn = new InPort<TimedPose2D>("pos", m_pos);
        m_joy_val = new TimedDoubleSeq();
        m_joy = new DataRef<TimedDoubleSeq>(m_joy_val);
        m_joyIn = new InPort<TimedDoubleSeq>("joy", m_joy);
        m_bump_val = new TimedBooleanSeq();
        m_bump = new DataRef<TimedBooleanSeq>(m_bump_val);
        m_bumpIn = new InPort<TimedBooleanSeq>("bumper", m_bump);
        m_vel_val = new TimedVelocity2D(new Time(0,0), new Velocity2D());
        m_vel = new DataRef<TimedVelocity2D>(m_vel_val);
        m_velOut = new OutPort<TimedVelocity2D>("vel", m_vel);
        // </rtc-template>

        frame = new ControllerFrame();
    }

    /**
     *
     * The initialize action (on CREATED->ALIVE transition)
     * formaer rtc_init_entry() 
     *
     * @return RTC::ReturnCode_t
     * 
     * 
     */
    @Override
    protected ReturnCode_t onInitialize() {
        // Registration: InPort/OutPort/Service
        // <rtc-template block="registration">
        // Set InPort buffers
        addInPort("pos", m_posIn);
        
        addInPort("joy", m_joyIn);
        addInPort("bumper", m_bumpIn);
        // Set OutPort buffer
        addOutPort("vel", m_velOut);
        // </rtc-template>
        return super.onInitialize();
    }

    /***
     *
     * The finalize action (on ALIVE->END transition)
     * formaer rtc_exiting_entry()
     *
     * @return RTC::ReturnCode_t
     * 
     * 
     */
//    @Override
//    protected ReturnCode_t onFinalize() {
//        return super.onFinalize();
//    }

    /***
     *
     * The startup action when ExecutionContext startup
     * former rtc_starting_entry()
     *
     * @param ec_id target ExecutionContext Id
     *
     * @return RTC::ReturnCode_t
     * 
     * 
     */
//    @Override
//    protected ReturnCode_t onStartup(int ec_id) {
//        return super.onStartup(ec_id);
//    }

    /***
     *
     * The shutdown action when ExecutionContext stop
     * former rtc_stopping_entry()
     *
     * @param ec_id target ExecutionContext Id
     *
     * @return RTC::ReturnCode_t
     * 
     * 
     */
//    @Override
//    protected ReturnCode_t onShutdown(int ec_id) {
//        return super.onShutdown(ec_id);
//    }

    /***
     *
     * The activated action (Active state entry action)
     * former rtc_active_entry()
     *
     * @param ec_id target ExecutionContext Id
     *
     * @return RTC::ReturnCode_t
     * 
     * 
     */
    @Override
    protected ReturnCode_t onActivated(int ec_id) {
        return super.onActivated(ec_id);
    }

    /***
     *
     * The deactivated action (Active state exit action)
     * former rtc_active_exit()
     *
     * @param ec_id target ExecutionContext Id
     *
     * @return RTC::ReturnCode_t
     * 
     * 
     */
    @Override
    protected ReturnCode_t onDeactivated(int ec_id) {
        return super.onDeactivated(ec_id);
    }

    /***
     *
     * The execution action that is invoked periodically
     * former rtc_active_do()
     *
     * @param ec_id target ExecutionContext Id
     *
     * @return RTC::ReturnCode_t
     * 
     * 
     */
    @Override
    protected ReturnCode_t onExecute(int ec_id) {
    	if(m_joyIn.isNew()) {
    		m_joyIn.read();
    		double x  = m_joy.v.data[0];
    		double y =  m_joy.v.data[1];
    		
    		m_vel.v.data.vx = 100 * y;
    		m_vel.v.data.vy = 0;
    		m_vel.v.data.va = -x / 2;
    		m_velOut.write();
    	}
    	
    	if(m_bumpIn.isNew()) {
    		m_bumpIn.read();
    		frame.setBump(m_bump.v.data[0], m_bump.v.data[1]);
    	}
    	
    	if(m_posIn.isNew()) {
    		m_posIn.read();
    		frame.setPose(m_pos.v.data.position.x, m_pos.v.data.position.y, m_pos.v.data.heading);
    	}
		frame.update();
        return super.onExecute(ec_id);
    }

    /***
     *
     * The aborting action when main logic error occurred.
     * former rtc_aborting_entry()
     *
     * @param ec_id target ExecutionContext Id
     *
     * @return RTC::ReturnCode_t
     * 
     * 
     */
//  @Override
//  public ReturnCode_t onAborting(int ec_id) {
//      return super.onAborting(ec_id);
//  }

    /***
     *
     * The error action in ERROR state
     * former rtc_error_do()
     *
     * @param ec_id target ExecutionContext Id
     *
     * @return RTC::ReturnCode_t
     * 
     * 
     */
//    @Override
//    public ReturnCode_t onError(int ec_id) {
//        return super.onError(ec_id);
//    }

    /***
     *
     * The reset action that is invoked resetting
     * This is same but different the former rtc_init_entry()
     *
     * @param ec_id target ExecutionContext Id
     *
     * @return RTC::ReturnCode_t
     * 
     * 
     */
    @Override
    protected ReturnCode_t onReset(int ec_id) {
        return super.onReset(ec_id);
    }

    /***
     *
     * The state update action that is invoked after onExecute() action
     * no corresponding operation exists in OpenRTm-aist-0.2.0
     *
     * @param ec_id target ExecutionContext Id
     *
     * @return RTC::ReturnCode_t
     * 
     * 
     */
//    @Override
//    protected ReturnCode_t onStateUpdate(int ec_id) {
//        return super.onStateUpdate(ec_id);
//    }

    /***
     *
     * The action that is invoked when execution context's rate is changed
     * no corresponding operation exists in OpenRTm-aist-0.2.0
     *
     * @param ec_id target ExecutionContext Id
     *
     * @return RTC::ReturnCode_t
     * 
     * 
     */
//    @Override
//    protected ReturnCode_t onRateChanged(int ec_id) {
//        return super.onRateChanged(ec_id);
//    }
//
    // DataInPort declaration
    // <rtc-template block="inport_declare">
    protected TimedPose2D m_pos_val;
    protected DataRef<TimedPose2D> m_pos;
    /*!
     */
    protected InPort<TimedPose2D> m_posIn;

    protected TimedDoubleSeq m_joy_val;
    protected DataRef<TimedDoubleSeq> m_joy;
    /*!
     */
    protected InPort<TimedDoubleSeq> m_joyIn;
    
    protected TimedBooleanSeq m_bump_val;
    protected DataRef<TimedBooleanSeq> m_bump;
    protected InPort<TimedBooleanSeq> m_bumpIn;
    
    // </rtc-template>

    // DataOutPort declaration
    // <rtc-template block="outport_declare">
    protected TimedVelocity2D m_vel_val;
    protected DataRef<TimedVelocity2D> m_vel;
    /*!
     */
    protected OutPort<TimedVelocity2D> m_velOut;

    
    // </rtc-template>

    // CORBA Port declaration
    // <rtc-template block="corbaport_declare">
    
    // </rtc-template>

    // Service declaration
    // <rtc-template block="service_declare">
    
    // </rtc-template>

    // Consumer declaration
    // <rtc-template block="consumer_declare">
    
    // </rtc-template>


}
