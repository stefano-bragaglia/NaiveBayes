package core;

import java.io.Serializable;
import java.util.*;

/**
 * TODO Add some meaningful class description...
 */
public class Processor<Sample, Feature> implements Serializable {

	private static final long serialVersionUID = 8866210182375973074L;

	private final ArrayList<Refiner<Sample>> sampleRefiners;

	private final Finder<Sample, Feature> finder;

	private final ArrayList<Refiner<Feature>> featureRefiners;

	private final Variant<Feature> variant;

	private Processor(Builder<Sample, Feature> builder) {
		Objects.requireNonNull(builder);

		this.finder = builder.finder;
		this.sampleRefiners = new ArrayList<>(builder.sampleRefiners);
		this.variant = builder.variant;
		this.featureRefiners = new ArrayList<>(builder.featureRefiners);
	}

	public Map<Feature, Double> process(Sample sample) {
		Objects.requireNonNull(sample);

		for (Refiner<Sample> refiner : sampleRefiners) {
			sample = refiner.process(sample);
		}
		List<Feature> features = new ArrayList<>();
		for (Feature feature : finder.process(sample)) {
			for (Refiner<Feature> refiner : featureRefiners) {
				feature = refiner.process(feature);
			}
			features.add(feature);
		}
		return variant.digest(features);
	}

	public static class Builder<Sample, Feature> {

		private List<Refiner<Sample>> sampleRefiners;

		private Finder<Sample, Feature> finder;

		private List<Refiner<Feature>> featureRefiners;

		private Variant<Feature> variant;

		public Builder(Finder<Sample, Feature> finder, Variant<Feature> variant) {
			Objects.requireNonNull(finder);
			Objects.requireNonNull(variant);

			this.finder = finder;
			this.sampleRefiners = new ArrayList<>();
			this.variant = variant;
			this.featureRefiners = new ArrayList<>();
		}

		public Builder<Sample, Feature> setFinder(Finder<Sample, Feature> finder) {
			Objects.requireNonNull(finder);

			this.finder = finder;
			return this;
		}

		public Builder<Sample, Feature> setVariant(Variant<Feature> variant) {
			Objects.requireNonNull(variant);

			this.variant = variant;
			return this;
		}

		public Builder<Sample, Feature> addSampleRefiner(Refiner<Sample> refiner) {
			Objects.requireNonNull(refiner);

			this.sampleRefiners.add(refiner);
			return this;
		}

		public Builder<Sample, Feature> removeSampleRefiner(Refiner<Sample> refiner) {
			Objects.requireNonNull(refiner);

			this.sampleRefiners.remove(refiner);
			return this;
		}

		public Builder<Sample, Feature> addSampleRefiners(Collection<Refiner<Sample>> refiners) {
			Objects.requireNonNull(refiners);

			for (Refiner<Sample> refiner : refiners) {
				Objects.requireNonNull(refiner);
				this.sampleRefiners.add(refiner);
			}
			return this;
		}

		public Builder<Sample, Feature> removeSampleRefiners(Collection<Refiner<Sample>> refiners) {
			Objects.requireNonNull(refiners);

			for (Refiner<Sample> refiner : refiners) {
				Objects.requireNonNull(refiner);
				this.sampleRefiners.remove(refiner);
			}
			return this;
		}

		public Processor<Sample, Feature> build() {
			return new Processor<>(this);
		}

	}
}
