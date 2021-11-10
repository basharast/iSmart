package bashar.astifan.ismart.smart.world.particlesystem.modifiers;


import bashar.astifan.ismart.smart.world.particlesystem.Particle;

public interface ParticleModifier {

	/**
	 * modifies the specific value of a particle given the current miliseconds
	 * @param particle
	 * @param miliseconds
	 */
	void apply(Particle particle, long miliseconds);

}
