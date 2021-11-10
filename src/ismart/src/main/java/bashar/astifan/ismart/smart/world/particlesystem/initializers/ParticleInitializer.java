package bashar.astifan.ismart.smart.world.particlesystem.initializers;

import java.util.Random;

import bashar.astifan.ismart.smart.world.particlesystem.Particle;


public interface ParticleInitializer {

	void initParticle(Particle p, Random r);

}
