################################################################################
# Automatically-generated file. Do not edit!
################################################################################

SHELL = cmd.exe

# Each subdirectory must supply rules for building sources it contributes
device/driverlib/%.obj: ../device/driverlib/%.c $(GEN_OPTS) | $(GEN_FILES) $(GEN_MISC_FILES)
	@echo 'Building file: "$<"'
	@echo 'Invoking: C2000 Compiler'
	"C:/ti/ccs1110/ccs/tools/compiler/ti-cgt-c2000_21.6.0.LTS/bin/cl2000" -v28 -ml -mt --cla_support=cla2 --float_support=fpu32 --tmu_support=tmu0 --vcu_support=vcu0 -Ooff --include_path="C:/ti/c2000/sci_ex4_echoback" --include_path="C:/ti/c2000/sci_ex4_echoback/device" --include_path="C:/ti/c2000/C2000Ware_4_00_00_00/driverlib/f28004x/driverlib" --include_path="C:/ti/ccs1110/ccs/tools/compiler/ti-cgt-c2000_21.6.0.LTS/include" --include_path="C:/ti/c2000/sci_ex4_echoback/device/driverlib/json.h" --define=DEBUG --define=_FLASH --define=CPU1 --define=_LAUNCHXL_F280049C --diag_suppress=10063 --diag_warning=225 --diag_wrap=off --display_error_number --abi=eabi --preproc_with_compile --preproc_dependency="device/driverlib/$(basename $(<F)).d_raw" --include_path="C:/ti/c2000/sci_ex4_echoback/CPU1_LAUNCHXL_FLASH/syscfg" --obj_directory="device/driverlib" $(GEN_OPTS__FLAG) "$<"
	@echo 'Finished building: "$<"'
	@echo ' '


