/**
 *  MQTT Bridge
 *
 *  Authors
 *   - st.john.johnson@gmail.com
 *   - jeremiah.wuenschel@gmail.com
 *   - raman325 (made small edits)
 *
 *  Copyright 2016
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 */
import groovy.json.JsonSlurper
import groovy.json.JsonOutput
import groovy.transform.Field

// Massive lookup tree
@Field CAPABILITY_MAP = [
    "accelerationSensors": [
        name: "Acceleration Sensor",
        capability: "capability.accelerationSensor",
        attributes: [
            "acceleration"
        ]
    ],
	"airQualitySensors": [
		name: "AirQuality Sensor",
		capability: "capability.airQualitySensor",
		attributes: [
			"airQuality"
		]
	],
    "alarm": [
        name: "Alarm",
        capability: "capability.alarm",
        attributes: [
            "alarm"
        ],
        action: "actionAlarm"
    ],
    "battery": [
        name: "Battery",
        capability: "capability.battery",
        attributes: [
            "battery"
        ]
    ],
    "beacon": [
        name: "Beacon",
        capability: "capability.beacon",
        attributes: [
            "presence"
        ]
    ],
    "button": [
        name: "Button",
        capability: "capability.button",
        attributes: [
            "button"
        ]
    ],
    "carbonDioxideMeasurement": [
        name: "Carbon Dioxide Measurement",
        capability: "capability.carbonDioxideMeasurement",
        attributes: [
            "carbonDioxide"
        ]
    ],
    "carbonMonoxideDetector": [
        name: "Carbon Monoxide Detector",
        capability: "capability.carbonMonoxideDetector",
        attributes: [
            "carbonMonoxide"
        ]
    ],
    "colorControl": [
        name: "Color Control",
        capability: "capability.colorControl",
        attributes: [
            "hue",
            "saturation",
            "color"
        ],
        action: "actionColor"
    ],
    "colorTemperature": [
        name: "Color Temperature",
        capability: "capability.colorTemperature",
        attributes: [
            "colorTemperature"
        ],
        action: "actionColorTemperature"
    ],
    "consumable": [
        name: "Consumable",
        capability: "capability.consumable",
        attributes: [
            "consumable"
        ],
        action: "actionConsumable"
    ],
    "contactSensors": [
        name: "Contact Sensor",
        capability: "capability.contactSensor",
        attributes: [
            "contact"
        ]
    ],
    "doorControl": [
        name: "Door Control",
        capability: "capability.doorControl",
        attributes: [
            "door"
        ],
        action: "actionOpenClosed"
    ],
	"dustSensors": [
		name: "Dust Sensor",
		capability: "capability.dustSensor",
		attributes: [
			"fineDustLevel",
			"dustLevel"
		]
	],
    "energyMeter": [
        name: "Energy Meter",
        capability: "capability.energyMeter",
        attributes: [
            "energy"
        ]
    ],
    "garageDoors": [
        name: "Garage Door Control",
        capability: "capability.garageDoorControl",
        attributes: [
            "door"
        ],
        action: "actionOpenClosed"
    ],
    "illuminanceMeasurement": [
        name: "Illuminance Measurement",
        capability: "capability.illuminanceMeasurement",
        attributes: [
            "illuminance"
        ]
    ],
    "imageCapture": [
        name: "Image Capture",
        capability: "capability.imageCapture",
        attributes: [
            "image"
        ]
    ],
    "levels": [
        name: "Switch Level",
        capability: "capability.switchLevel",
        attributes: [
            "level"
        ],
        action: "actionLevel"
    ],
    "lock": [
        name: "Lock",
        capability: "capability.lock",
        attributes: [
            "lock"
        ],
        action: "actionLock"
    ],
    "mediaController": [
        name: "Media Controller",
        capability: "capability.mediaController",
        attributes: [
            "activities",
            "currentActivity"
        ]
    ],
    "motionSensors": [
        name: "Motion Sensor",
        capability: "capability.motionSensor",
        attributes: [
            "motion"
        ],
        action: "actionActiveInactive"
    ],
    "musicPlayer": [
        name: "Music Player",
        capability: "capability.musicPlayer",
        attributes: [
            "status",
            "level",
            "trackDescription",
            "trackData",
            "mute"
        ],
        action: "actionMusicPlayer"
    ],
    "pHMeasurement": [
        name: "pH Measurement",
        capability: "capability.pHMeasurement",
        attributes: [
            "pH"
        ]
    ],
    "powerMeters": [
        name: "Power Meter",
        capability: "capability.powerMeter",
        attributes: [
            "power"
        ]
    ],
    "presenceSensors": [
        name: "Presence Sensor",
        capability: "capability.presenceSensor",
        attributes: [
            "presence"
        ],
        action: "actionPresence"
    ],
    "humiditySensors": [
        name: "Relative Humidity Measurement",
        capability: "capability.relativeHumidityMeasurement",
        attributes: [
            "humidity"
        ]
    ],
    "relaySwitch": [
        name: "Relay Switch",
        capability: "capability.relaySwitch",
        attributes: [
            "switch"
        ],
        action: "actionOnOff"
    ],
    "sceneSwitch": [
        name: "Scene Switch",
        capability: "capability.switch",
        attributes: [
            "scene"
        ]
    ],
    "shockSensor": [
        name: "Shock Sensor",
        capability: "capability.shockSensor",
        attributes: [
            "shock"
        ]
    ],
    "signalStrength": [
        name: "Signal Strength",
        capability: "capability.signalStrength",
        attributes: [
            "lqi",
            "rssi"
        ]
    ],
    "sleepSensor": [
        name: "Sleep Sensor",
        capability: "capability.sleepSensor",
        attributes: [
            "sleeping"
        ]
    ],
    "smokeDetector": [
        name: "Smoke Detector",
        capability: "capability.smokeDetector",
        attributes: [
            "smoke"
        ]
    ],
    "soundSensor": [
        name: "Sound Sensor",
        capability: "capability.soundSensor",
        attributes: [
            "sound"
        ]
    ],
    "stepSensor": [
        name: "Step Sensor",
        capability: "capability.stepSensor",
        attributes: [
            "steps",
            "goal"
        ]
    ],
    "switches": [
        name: "Switch",
        capability: "capability.switch",
        attributes: [
            "switch"
        ],
        action: "actionOnOff"
    ],
    "soundPressureLevel": [
        name: "Sound Pressure Level",
        capability: "capability.soundPressureLevel",
        attributes: [
            "soundPressureLevel"
        ]
    ],
    "tamperAlert": [
        name: "Tamper Alert",
        capability: "capability.tamperAlert",
        attributes: [
            "tamper"
        ]
    ],
    "temperatureSensors": [
        name: "Temperature Measurement",
        capability: "capability.temperatureMeasurement",
        attributes: [
            "temperature"
        ]
    ],
    "thermostat": [
        name: "Thermostat",
        capability: "capability.thermostat",
        attributes: [
            "temperature",
            "heatingSetpoint",
            "coolingSetpoint",
            "thermostatSetpoint",
            "thermostatMode",
            "thermostatFanMode",
            "thermostatOperatingState"
        ],
        action: "actionThermostat"
    ],
    "thermostatCoolingSetpoint": [
        name: "Thermostat Cooling Setpoint",
        capability: "capability.thermostatCoolingSetpoint",
        attributes: [
            "coolingSetpoint"
        ],
        action: "actionCoolingThermostat"
    ],
    "thermostatFanMode": [
        name: "Thermostat Fan Mode",
        capability: "capability.thermostatFanMode",
        attributes: [
            "thermostatFanMode"
        ],
        action: "actionThermostatFan"
    ],
    "thermostatHeatingSetpoint": [
        name: "Thermostat Heating Setpoint",
        capability: "capability.thermostatHeatingSetpoint",
        attributes: [
            "heatingSetpoint"
        ],
        action: "actionHeatingThermostat"
    ],
    "thermostatMode": [
        name: "Thermostat Mode",
        capability: "capability.thermostatMode",
        attributes: [
            "thermostatMode"
        ],
        action: "actionThermostatMode"
    ],
    "thermostatOperatingState": [
        name: "Thermostat Operating State",
        capability: "capability.thermostatOperatingState",
        attributes: [
            "thermostatOperatingState"
        ]
    ],
    "thermostatSetpoint": [
        name: "Thermostat Setpoint",
        capability: "capability.thermostatSetpoint",
        attributes: [
            "thermostatSetpoint"
        ]
    ],
    "threeAxis": [
        name: "Three Axis",
        capability: "capability.threeAxis",
        attributes: [
            "threeAxis"
        ]
    ],
    "timedSession": [
        name: "Timed Session",
        capability: "capability.timedSession",
        attributes: [
            "timeRemaining",
            "sessionStatus"
        ],
        action: "actionTimedSession"
    ],
    "touchSensor": [
        name: "Touch Sensor",
        capability: "capability.touchSensor",
        attributes: [
            "touch"
        ]
    ],
    "ultravioletIndex": [
        name: "UV Index",
        capability: "capability.ultravioletIndex",
        attributes: [
            "ultravioletIndex"
        ]
    ],
    "valve": [
        name: "Valve",
        capability: "capability.valve",
        attributes: [
            "contact"
        ],
        action: "actionOpenClosed"
    ],
    "voltageMeasurement": [
        name: "Voltage Measurement",
        capability: "capability.voltageMeasurement",
        attributes: [
            "voltage"
        ]
    ],
    "waterSensors": [
        name: "Water Sensor",
        capability: "capability.waterSensor",
        attributes: [
            "water"
        ]
    ],
    "windowShades": [
        name: "Window Shade",
        capability: "capability.windowShade",
        attributes: [
            "windowShade"
        ],
        action: "actionOpenClosed"
    ]
]

@Field LOCATION_BASE_ATTRS = [
    "temperatureScale",
    "name",
    "id"
]

@Field LOCATION_POSITION_ATTRS = [
    "latitude",
    "longitude",
    "zipCode",
    "timeZone"
]

definition(
    name: "MQTT Bridge",
    namespace: "raman325",
    author: "St. John Johnson, Jeremiah Wuenschel, and Raman Gupta",
    description: "A bridge between SmartThings and MQTT",
    category: "My Apps",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Connections/Cat-Connections.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Connections/Cat-Connections@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Connections/Cat-Connections@3x.png"
)

preferences {
    page (name:"hubLevelInputs")
}

def hubLevelInputs() {
    dynamicPage(name: "hubLevelInputs", title: "Select Options", install: true, uninstall: true) {
        section("Send Notifications?") {
            input("recipients", "contact", title: "Send notifications to", multiple: true, required: false)
        }

        section ("Location Level Inputs") {
            input("trackLocationMode", "bool", title: "Mode", multiple: false, required: false)
            input("locationPosition", "bool", title: "Position Attributes", multiple: false, required: false)
            input("locationSun", "bool", title: "Sunrise and Sunset Times", multiple: false, required: false)

            def actions = location.helloHome?.getPhrases()*.label
            if (actions) {
                actions.sort()
                input("routines", "enum", title: "Routines", multiple: true, required: false, options: actions)
            }
        }

        section ("Device Level Inputs") {
            CAPABILITY_MAP.each { key, capability ->
                input key, capability["capability"], title: capability["name"], multiple: true, required: false
            }
        }

        section ("Bridge") {
            input "bridge", "capability.notification", title: "Notify this Bridge", required: true, multiple: false
        }
    }
}

def installed() {
    log.debug "Installed with settings: ${settings}"

    runEvery15Minutes(initialize)
    initialize()
}

def updated() {
    log.debug "Updated with settings: ${settings}"

    // Unsubscribe from all events
    unsubscribe()
    // Subscribe to stuff
    initialize()
}

// Return list of displayNames
def getDeviceNames(devices) {
    def list = []
    devices.each{device->
        list.push(device.displayName)
    }
    list
}

def initialize() {
    // Subscribe to new events from devices
    CAPABILITY_MAP.each { key, capability ->
        capability["attributes"].each { attribute ->
            subscribe(settings[key], attribute, deviceHandler)
        }
    }

    // Subscribe to mode change events from Location
    if (trackLocationMode) {
        subscribe(location, "mode", locationHandler)
    }

    // Subscribe to sunrise/sunset change events from Location
    if (locationSun) {
        subscribe(location, "sunsetTime", sunsetHandler)
        subscribe(location, "sunriseTime", sunriseHandler)
    }

    // Subscribe to position change events from location
    if (locationPosition) {
        subscribe(location, "position", positionHandler)
    }

    // Subscribe to routine execution events from Location
    if (routines && routines.size() > 0) {
        subscribe(location, "routineExecuted", routineHandler)
    }

    // Subscribe to events from the bridge
    subscribe(bridge, "message", bridgeHandler)

    // Update the bridge
    updateSubscription()
}

// Update the bridge"s subscription
def updateSubscription() {
    def attributes = [
        notify: ["Contacts", "System"]
    ]

    if (trackLocationMode || locationPosition || locationSun) {
        updateLocationAttributes(LOCATION_BASE_ATTRS)

        if (trackLocationMode) {
            attributes["mode"] = ["Hub"]
            locationHandler([
                name: "mode",
                value: location.mode
            ])
        }

        if (locationPosition) {
            updateLocationAttributes(LOCATION_POSITION_ATTRS)
        }

        if (locationSun) {
            locationHandler([
                name: "sunriseTime",
                value: getSunriseAndSunset().sunrise
            ])
            locationHandler([
                name: "sunsetTime",
                value: getSunriseAndSunset().sunset
            ])
        }
    }

    if (routines) {
        attributes["execute"] = ["Routine"]
    }

    CAPABILITY_MAP.each { key, capability ->
        capability["attributes"].each { attribute ->
            if (!attributes.containsKey(attribute)) {
                attributes[attribute] = []
            }
            settings[key].each {device ->
                attributes[attribute].push(device.displayName)

                deviceHandler([
                    "displayName": device.displayName, 
                    "name": attribute, 
                    "value": device.currentValue(attribute)
                ])
            }
        }
    }
    def json = new groovy.json.JsonOutput().toJson([
        path: "/subscribe",
        body: [
            devices: attributes
        ]
    ])

    log.debug "Updating subscription: ${json}"

    bridge.deviceNotification(json)
}

// Receive an event from the bridge
def bridgeHandler(evt) {
    def json = new JsonSlurper().parseText(evt.value)
    log.debug "Received device event from bridge: ${json}"

    if (json.type == "notify") {
        if (json.name == "Contacts") {
            sendNotificationToContacts("${json.value}", recipients)
        } else {
            sendNotificationEvent("${json.value}")
        }
        return
    }

    if (
        json.name == "Hub"
        && location.mode != json.value
    ) {
        if (json.type == "mode") {
            if (location.modes?.find {it.name == json.value})
            {
                location.setMode(json.value)
                state.ignoreEvent = json
            }
            else
            {
                log.warn "Ignoring invalid hub mode of '${json.value}'. Valid options are '${location.modes}'"
                def resetAttribute = [
                    "value": location.mode,
                    "name": json.type
                ]
                locationHandler(resetAttribute)
            }
        }
        else {
            log.warn "Ignoring update to '${json.type}' since it can't be updated and resetting value"
            def resetAttribute = [
                "value": location."$json.type",
                "name": json.type
            ]
            locationHandler(resetAttribute)
        }
        return
    }

    if (
        json.type == "execute"
        && json.name == "Routine"
    ) {
        def routineName = json.value
        if (location.helloHome?.getPhrases()*.label?.find {it == routineName}) {
            location.helloHome?.execute(routineName)
        }
        else {
            log.warn "Routine ${routineName} doesn't exist so there is nothing to execute."
        }
    }


    // @NOTE this is stored AWFUL, we need a faster lookup table
    // @NOTE this also has no fast fail, I need to look into how to do that
    CAPABILITY_MAP.each { key, capability ->
        if (capability["attributes"].contains(json.type)) {
            settings[key].each {device ->
                if (device.displayName == json.name) {
                    if (json.command == false) {
                        if (device.getSupportedCommands().any {it.name == "setStatus"}) {
                            log.debug "Setting state ${json.type} = ${json.value}"
                            device.setStatus(json.type, json.value)
                            state.ignoreEvent = json;
                        }
                    }
                    else {
                        if (capability.containsKey("action")) {
                            def action = capability["action"]
                            // Yes, this is calling the method dynamically
                            if ("$action"(device, json.type, json.value) == true) {
                                return
                            }
                            else {
                                if (device.hasAttribute(json.type)) {
                                    log.warn "Value '${json.value}' not valid for device.attribute '${device.displayName}.${json.type}'."
                                    def resetAttribute = [
                                        "displayName": device.displayName,
                                        "value": device.currentValue(json.type),
                                        "name": json.type
                                    ]
                                    deviceHandler(resetAttribute)
                                }
                                else {
                                    log.warn "Attribute '${json.type}' not available for device '${device.displayName}'"
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// Receive an event from a device
def deviceHandler(evt) {
    pushNotification(evt.displayName, evt.name, evt.value, true)
}

// Receive an event from a location
def locationHandler(evt) {
    pushNotification("Hub", evt.name, evt.value, true)
}

// Receive a physical location change event from a location
def positionHandler(evt) {
    updateLocationAttributes(LOCATION_BASE_ATTRS)
    updateLocationAttributes(LOCATION_POSITION_ATTRS)
}

def sunriseHandler(evt) {
    locationHandler([
        name: "sunsetTime",
        value: getSunriseAndSunset().sunset
    ])
}

def sunsetHandler(evt) {
    locationHandler([
        name: "sunriseTime",
        value: getSunriseAndSunset().sunrise
    ])
}

// Update values of list of location attributes
def updateLocationAttributes(attributes) {
    attributes.each { attribute ->
        if (attribute == "timeZone") {
            locationHandler([
                name: "timeZone",
                value: location.timeZone.getID()
            ])
        }
        else {
            locationHandler([
                name: attribute,
                value: location."$attribute"
            ])
        }
    }
}

// Receive a routine execution event from a device
def routineHandler(evt) {
    if (routines.any { it == evt.displayName }) {
        pushNotification("Routine: ${evt.displayName}", "lastExecution", evt.date, false)
        pushNotification("Routine", "lastRoutineExecuted", evt.displayName, false)
    }
}

// Create event to send to device handler
def pushNotification(name, type, value, checkIgnore) {
    if (
        (
            checkIgnore
            && state.ignoreEvent
            && state.ignoreEvent.name == name
            && state.ignoreEvent.type == type
            && state.ignoreEvent.value == value
        )
        || value == null
        
    ) {
        def evt = [
            name: name,
            type: type,
            value: value
        ]
        log.debug "Ignoring event ${evt}"
        state.ignoreEvent = false;
    }
    else {
        def json = new JsonOutput().toJson([
            path: "/push",
            body: [
                name: name,
                value: (value as String),
                type: type
            ]
        ])

        log.debug "Forwarding device event to bridge: ${json}"
        bridge.deviceNotification(json)
    }
}

// +---------------------------------+
// | WARNING, BEYOND HERE BE DRAGONS |
// +---------------------------------+
// These are the functions that handle incoming messages from MQTT.
// I tried to put them in closures but apparently SmartThings Groovy sandbox
// restricts you from running clsures from an object (it's not safe).

def actionAlarm(device, attribute, value) {
    switch (value) {
        case "strobe":
            device.strobe()
        break
        case "siren":
            device.siren()
        break
        case "off":
            device.off()
        break
        case "both":
            device.both()
        break
        default:
            return false
        
        return true
    }
}

def actionColor(device, attribute, value) {
    switch (attribute) {
        case "hue":
            device.setHue(value as float)
        break
        case "saturation":
            device.setSaturation(value as float)
        break
        case "color":
            def values = value.split(',')
            def colormap = ["hue": values[0] as float, "saturation": values[1] as float]
            device.setColor(colormap)
        break
        default:
            return false
        
        return true
    }
}

def actionOpenClosed(device, attribute, value) {
    if (value == "open") {
        device.open()
    } else if (value == "closed") {
        device.close()
    }
    else {
        return false
    }
    
    return true
}

def actionOnOff(device, attribute, value) {
    if (value == "off") {
        device.off()
    } else if (value == "on") {
        device.on()
    }
    else {
        return false
    }
    
    return true
}

def actionActiveInactive(device, attribute, value) {
    if (value == "active") {
        device.active()
    } else if (value == "inactive") {
        device.inactive()
    }
    else {
        return false
    }
    
    return true
}

def actionThermostat(device, attribute, value) {
    switch(attribute) {
        case "heatingSetpoint":
            device.setHeatingSetpoint(value as float)
        break
        case "coolingSetpoint":
            device.setCoolingSetpoint(value as float)
        break
        case "thermostatMode":
            if (device.currentValue("supportedThermostatModes")?.find {it == value}) {
                device.setThermostatMode(value)
            }
            else {
                log.warn "Thermostat mode of '${value}' not a valid option for device '${device.displayName}'. Valid options are '${device.currentValue("supportedThermostatModes")}'"
                def resetAttribute = [
                    "displayName": device.displayName,
                    "value": device.currentValue(attribute),
                    "name": attribute
                ]
                deviceHandler(resetAttribute)
            }
        break
        case "thermostatFanMode":
            if (device.currentValue("supportedThermostatFanModes")?.find {it == value}) {
                device.setThermostatFanMode(value)
            }
            else {
                log.warn "Thermostat fan mode of '${value}' not a valid option for device '${device.displayName}'. Valid options are '${device.currentValue("supportedThermostatFanModes")}'"
                def resetAttribute = [
                    "displayName": device.displayName,
                    "value": device.currentValue(attribute),
                    "name": attribute
                ]
                deviceHandler(resetAttribute)
            }
        break
        default:
            return false
        
        return true
    }
}

def actionMusicPlayer(device, attribute, value) {
    switch(attribute) {
        case "level":
            device.setLevel(value)
        break
        case "mute":
            if (value == "muted") {
                device.mute()
            } else if (value == "unmuted") {
                device.unmute()
            }
        break
        case "status":
            if (device.getSupportedCommands().any {it.name == "setStatus"}) {
                device.setStatus(value)
            }
        break
        default:
            return false
        
        return true
    }
}

def actionColorTemperature(device, attribute, value) {
    device.setColorTemperature(value as int)
    return true
}

def actionLevel(device, attribute, value) {
    device.setLevel(value as int)
    return true
}

def actionPresence(device, attribute, value) {
    if (value == "present") {
        device.arrived();
    }
    else if (value == "not present") {
        device.departed();
    }
    else {
        return false
    }
    
    return true
}

def actionConsumable(device, attribute, value) {
    device.setConsumableStatus(value)
}

def actionLock(device, attribute, value) {
    if (value == "locked") {
        device.lock()
    } else if (value == "unlocked") {
        device.unlock()
    }
    else {
        return false
    }
    
    return true
}

def actionCoolingThermostat(device, attribute, value) {
    device.setCoolingSetpoint(value as float)
    return true
}

def actionThermostatFan(device, attribute, value) {
    if (attribute != "thermostatFanMode") {
        return false
    }
    else {
        if (device.currentValue("supportedThermostatFanModes")?.find {it == value}) {
            device.setThermostatFanMode(value)
        }
        else {
            log.warn "Thermostat fan mode of '${value}' not a valid option for device '${device.displayName}'. Valid options are '${device.currentValue("supportedThermostatFanModes")}'"
            def resetAttribute = [
                "displayName": device.displayName,
                "value": device.currentValue(attribute),
                "name": attribute
            ]
            deviceHandler(resetAttribute)
        }
        return true
    }
}

def actionHeatingThermostat(device, attribute, value) {
    device.setHeatingSetpoint(value as float)
    return true
}

def actionThermostatMode(device, attribute, value) {
    if (attribute != "thermostatMode") {
        return false
    }
    else {
        if (device.currentValue("supportedThermostatModes")?.find {it == value}) {
            device.setThermostatMode(value)
        }
        else {
            log.warn "Thermostat fan mode of '${value}' not a valid option for device '${device.displayName}'. Valid options are '${device.currentValue("supportedThermostatModes")}'"
            def resetAttribute = [
                "displayName": device.displayName,
                "value": device.currentValue(attribute),
                "name": attribute
            ]
            deviceHandler(resetAttribute)
        }
        return true
    }
}

def actionTimedSession(device, attribute, value) {
    if (attribute == "timeRemaining") {
        device.setTimeRemaining(value)
        return true
    }
    else {
        return false
    }
}
