package art.nedink.processingphysics.cutec2;

/**
 * IMPORTANT:
 * Many algorithms in this file are sensitive to the magnitude of the
 * ray direction (c2Ray::d). It is highly recommended to normalize the
 * ray direction and use t to specify a distance. Please see this link
 * for an in-depth explanation: https://github.com/RandyGaul/cute_headers/issues/30
 */
public class C2Ray {

    C2v p;   // position
    C2v d;   // direction (normalized)
    float t; // distance along d from position p to find endpoint of ray

}
