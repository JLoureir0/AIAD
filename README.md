# T06: Multi-Agent Reinforcement Learning to Watershed Management

## Objective
Implement a multi-agent reinforcement learning to watershed management.

## Description
Water is an essential and limited resource, for that reason it's management must be efficient. Consider the existence of
a river and a set of entities interested in using it: water supply of a city, irrigation of agricultural area (2 farms),
power generation, fish protection ecologic zone.

The different entities interested in the use of water have separate goals that are mapped in different utility functions.
Each entity is represented by an agent that controls the quantity of water to be retrieved from the river for it to use,
in order to meet its goals and some restrictions(for instance, the minimum water level of the river after the
water to supply the city being retrieved). Consult the mentioned bibliography for an appropriate description of the
restrictions to consider.

The multi-agent system to implement must regulate the water used by all the interested entities, keeping the balance
between the goals/interests of all the agents and the restrictions applied. Reinforcement learning is used in this
regulation.

# Material
* JADE, Repast+SAJaS
* [Karl Mason, Patrick Mannion, Jim Duggan and Edna Howley (2016). Applying Multi-Agent Reinforcement Learning to
Watershed Management.](
https://www.researchgate.net/publication/299416955_Applying_Multi-Agent_Reinforcement_Learning_to_Watershed_Management)
* [Francesco Amigoni, Andrea Castelletti and Matteo Giuliani (2015), Modeling the Management of Water Resources Systems
Using Multi-Objective DCOPs, Proceedings of the 2015 International Conference on Autonomous Agents and Multiagent
Systems, Istanbul, Turkey](
https://www.researchgate.net/publication/299416955_Applying_Multi-Agent_Reinforcement_Learning_to_Watershed_Management)
