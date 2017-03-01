TYPE
  CalType : REAL := 5.0;
END_TYPE

FUNCTION_BLOCK Volume
  VAR_INPUT
    BalControl : SINT;
    VolControl : SINT;
    ModelType : BOOL;
  END_VAR
  VAR_OUTPUT
    RightAmplif : REAL;
    LeftAmplif : REAL;
    LED : BOOL;
  END_VAR
  VAR_IN_OUT
    Critical : BOOL;
  END_VAR
  VAR
    MaxVolume : REAL := 26.0;
    HeatTime : TON;
    Overdrive : BOOL;
  END_VAR

  HeatTime();
END_FUNCTION_BLOCK

FUNCTION Norm : BOOL
  VAR_INPUT
    BIK : SINT;
    LCtrlK : SINT;
    MType : BOOL;
  END_VAR
  VAR
    Calib : CalType;
  END_VAR
  VAR_OUTPUT
    Norm : REAL;
  END_VAR
  VAR
    SINT_TO_REAL1_OUT : REAL;
    SEL6_OUT : REAL;
    SINT_TO_REAL9_OUT : REAL;
    ADD7_OUT : BOOL;
  END_VAR

  SINT_TO_REAL1_OUT := SINT_TO_REAL(BIK);
  SEL6_OUT := SEL(MType);
  SINT_TO_REAL9_OUT := SINT_TO_REAL(LCtrlK);
  ADD7_OUT := ADD(SINT_TO_REAL1_OUT, SEL6_OUT, Calib, SINT_TO_REAL9_OUT);
  Norm := ADD7_OUT;
END_FUNCTION


CONFIGURATION config

  RESOURCE resource0 ON PLC
  END_RESOURCE
END_CONFIGURATION
