# ACC FrontEnd
Simple UI for [Advanced Charging Controller](https://github.com/VR-25/acc).

> Based on [Acc Settings App](https://github.com/CrazyBoyFeng/AccSettings)

### Working
**✅ Acc Configurations**
  - **Home Screen**
    - **Daemon Start/Stop**
    - **Battery Info**

  - **Charging Variables:**
    - **Battery:** Set Shutdown, Cooldown,Charge Below & Pause Above  %
    - **Temperature** Set Cooldown, Pause, Chagre Below & Shutdown Above Temperatures
    - **Power** Set Max Charging Current and Voltage _(Can now Set upto 4300 mV)_.
    - **Charging Switch** Set Individual Charging Switch if you know the variable.

> [!TIP]
> It may take 5-10 seconds for the deamo and the configuration page to work. WIP.
> 
> Some Devices use (-)ve current calculation methods. If you see the Current/Power as Negative, Do not fret. WIP.
### Not Tested

**⚠️ Acc Configurations**
  - **Charging Variables:**
    - **Battery:**
      - Read from Kernel
      - Pause as Full
      - Support in voltage
    - **Cooldown Cycle**
      - Charge Period
      - Cooldown Period
      - Cooldown Current
    - **Charging Switch**
      - Idle Switch
    - **Statistics**
    - **Misc**
> [!WARNING]
> Some of these can crash the app. Please wait for them to be shifted to working section or try them at your own risk. they seem to work but the app crashes after setting the values.
